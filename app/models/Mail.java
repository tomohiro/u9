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

    @Required
    @Email
    @Column(name="to_address")
    public String to;

    @Email
    @Column(name="cc_address")
    public String cc;

    @Email
    @Column(name="bcc_address")
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

    public boolean send() {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setFrom(this.user.email);
            for (String to: Mail.getEmailAddressList(this.to)) {
                email.addTo(to);
            }
            if (this.cc != null && !this.cc.isEmpty()) {
                for (String cc: Mail.getEmailAddressList(this.cc)) {
                    email.addCc(cc);
                }
            }
            if (this.bcc != null && !this.bcc.isEmpty()) {
                for (String bcc: Mail.getEmailAddressList(this.bcc)) {
                    email.addBcc(bcc);
                }
            }
            email.setSubject(this.template.subject);
            email.setContent(this.template.body, "text/plain; charset=ISO-2022-JP");
            email.setCharset("ISO-2022-JP");
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
