package models;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
@Table(name="templates")
public class Template extends Model {

    public String name;
    public String subject;

    @Lob
    public String body;

    @ManyToOne
    public User user;

    @OneToMany(mappedBy="template", cascade=CascadeType.ALL)
    public List<Mail> mails;

    public Template(String name, String subject, String body, User user) {
        this.name    = name;
        this.subject = subject;
        this.body    = body;
        this.user    = user;
        this.mails   = new ArrayList<Mail>();
    }

}
