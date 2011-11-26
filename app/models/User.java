package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.JsonObject;
 
import play.db.jpa.*;
import play.libs.WS;

@Entity
@Table(name="users")
public class User extends GenericModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;

    public String facebookId;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    public List<Mail> mails;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    public List<Template> templates;

    public User(String facebookId) {
        this.mails = new ArrayList<Mail>();
        this.templates = new ArrayList<Template>();
        this.facebookId = facebookId;
    }

}
