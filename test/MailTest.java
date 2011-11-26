import org.junit.*;

import java.util.List;

import play.db.jpa.JPABase;
import play.test.*;
import models.*;

public class MailTest extends UnitTest {

    @Test
    public void createMail() {
    	User testUser = new User("test access token");
    	new Mail("test mail name", "test name subject", testUser).save();
    	Mail mail = Mail.find("byName", "test mail name").first();
    	assertEquals(mail.subject, "test mail subject");
    	assertEquals(mail.user.facebookAccessToken, "test access token");
    }

}
