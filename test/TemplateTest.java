import org.junit.*;

import java.util.List;
import play.test.*;
import models.*;

public class TemplateTest extends UnitTest {

    @Test
    public void create() {
        User user = new User().save();
        String name = "My Template Name";
        String subject = "My Mail Subject";
        Template template = new Template(name, subject, "body", user);
        template.save();

        Template findTemplte = Template.findById(template);

        assertEquals(findTemplte.name, name);
    }

}
