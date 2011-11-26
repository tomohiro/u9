package models;

import javax.persistence.*;
 
import play.db.jpa.*;

@Entity
@Table(name="users")
public class User extends Model {

    @Column(name="facebook_access_token")
    public String facebookAccessToken;

    public User() { }

    public User(String facebookAccessToken) {
        this.facebookAccessToken = facebookAccessToken;
    }

}
