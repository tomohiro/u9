package models;

import java.util.Date;

import javax.persistence.*;

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

    public boolean send() {
        this.sendAt = new Date();
        save();
        return true;
    }

}
