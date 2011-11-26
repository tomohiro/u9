import org.junit.*;

import java.util.List;

import play.db.jpa.JPABase;
import play.test.*;
import models.*;

public class MailTest extends UnitTest {

    @Test
    public void createMail() {
        User testUser = new User();
        Template testTemplate = new Template("name", "subject", "body", testUser);

        Mail mail = new Mail(testTemplate, testUser);
        mail.save();

        Mail findMail = Mail.findById(mail);

        assertEquals(findMail.template.subject, "test mail subject");
        assertEquals(findMail.user.facebookAccessToken, "test access token");
    }

}
