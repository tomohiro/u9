package models;

import javax.persistence.*;

import play.db.jpa.*;

@Entity
@Table(name="mails")
public class Mail extends Model {

    public String to;
    public String cc;
    public String bcc;

    @ManyToOne
    public Template template;

    @ManyToOne
    public User user;

    public Mail(Template template, User user) {
        this.user = user;
        this.template = template;
    }

}
