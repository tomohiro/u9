import org.junit.*;

import java.util.List;

import play.db.jpa.JPABase;
import play.test.*;
import models.*;

public class MailTest extends UnitTest {

    @Test
    public void createMail() {
        User testUser = new User();
        testUser.save();
        Template testTemplate = new Template("name", "test mail subject", "body", testUser);
        testTemplate.save();

        Mail mail = new Mail(testTemplate, testUser);
        mail.save();

        Mail findMail = Mail.findById(mail.id);

        assertEquals(findMail.template.subject, testTemplate.subject);
        assertEquals(findMail.user.facebookAccessToken, testUser.facebookAccessToken);
    }

}
