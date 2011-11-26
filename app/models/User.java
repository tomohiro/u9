package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.JsonObject;
 
import play.data.validation.Email;
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

    @Email
    public String email;

    public String name;

    public String accessToken;

    public User(String facebookId) {
        this.facebookId = facebookId;

        this.mails     = new ArrayList<Mail>();
        this.templates = new ArrayList<Template>();
    }

    /** @TODO return user email from facebook api */
    public String email() {
        return "example@example.com";
    }
}
