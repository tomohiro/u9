import org.junit.*;

import java.util.Date;
import java.util.List;

import play.db.jpa.JPABase;
import play.test.*;
import models.*;

public class MailTest extends UnitTest {

    @Test
    public void create() {
        User testUser = new User("facebookId").save();
        Template testTemplate = new Template("name", "test mail subject", "body", testUser).save();

        Mail mail = new Mail(testTemplate, testUser);
        mail.save();

        Mail findMail = Mail.findById(mail.id);

        assertEquals(findMail.template.subject, testTemplate.subject);
        assertEquals(findMail.user.facebookId, testUser.facebookId);
    }

    @Test
    public void send() {
        User testUser = new User("facebookId").save();
        testUser.email = "example@example.com";
        
        Template testTemplate = new Template("name", "Play! test mail", "This mail is Play! test message.", testUser).save();

        Mail mail = new Mail(testTemplate, testUser);
        mail.to = "tomohiro.t+to@gmail.com";
        assertTrue(mail.send());

        mail.cc = "tomohiro.t+cc1@gmail.com, tomohiro.t+cc2@gmail.com";
        assertTrue(mail.send());

        mail.bcc = "tomohiro.t+bcc@gmail.com, tomohiro.t+bcc@gmail.com";
        assertTrue(mail.send());

        mail.to = "tomohiro.tgmail.com";
        assertFalse(mail.send());
    }

}
