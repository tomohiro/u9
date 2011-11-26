package models;

import javax.persistence.*;

import play.db.jpa.*;

@Entity
@Table(name="mails")
public class Mail extends Model {
	public String name;
	public String subject;
	
	@Lob
	public String body;
	
	public String to;
	public String cc;
	public String bcc;
	
	@ManyToOne
	public User user;
	
	public Mail(String name, String subject, User user) {
		this.name = name;
		this.subject = subject;
		this.user = user;
	}
}
