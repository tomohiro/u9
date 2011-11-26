package models;

import javax.persistence.*;
 
import play.db.jpa.*;

@Entity
@Table(name="users")
public class User extends Model {
	
	public String facebookAccessToken;
	
	public User(String facebookAccessToken) {
		this.facebookAccessToken = facebookAccessToken;
	}
}
