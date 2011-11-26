package models;

import java.util.Date;

import javax.persistence.*;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import play.db.jpa.*;

@Entity
@Table(name="mails")
public class Mail extends Model {

    public String to;
    public String cc;
    public String bcc;

    @Column(name="send_at")
    public Date sendAt;

    @ManyToOne
    public Template template;

    @ManyToOne
    public User user;

    public Mail(Template template, User user) {
        this.user = user;
        this.template = template;
    }

    public String from()
    {
        return this.user.email();
    }

    public boolean send() {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setFrom(from());
            email.addTo(this.to);
            if (this.cc != null) {
                email.addCc(this.cc);
            }
            if (this.bcc != null) {
                email.addBcc(this.bcc);
            }
            email.setSubject(this.template.subject);
            email.setMsg(this.template.body);
            play.libs.Mail.send(email); 

            this.sendAt = new Date();
            save();
        } catch (EmailException e) {
           e.printStackTrace();
           return false;
        }
        return true;
    }

}
