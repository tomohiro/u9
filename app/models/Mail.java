package models;

import java.util.Date;

import javax.persistence.*;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import play.db.jpa.*;
import play.data.validation.*;
@Entity
@Table(name="mails")
public class Mail extends Model {

    @Email
    public String to;

    @Email
    public String cc;

    @Email
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

    public static String[] getEmailAddressList(String emails) {
        return emails.split(",", -1);
    }

    public String from()
    {
        return this.user.email();
    }

    public boolean send() {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setFrom(from());
            if (this.to != null) {
                for (String to: Mail.getEmailAddressList(this.to)) {
                    email.addBcc(to);
                }
            }
            email.addTo(this.to);
            if (this.cc != null) {
                for (String cc: Mail.getEmailAddressList(this.cc)) {
                    email.addCc(cc);
                }
            }
            if (this.bcc != null) {
                for (String to: Mail.getEmailAddressList(this.bcc)) {
                    email.addBcc(to);
                }
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
