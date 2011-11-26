import org.junit.*;

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

        Template findTemplate = Template.findById(template.id);

        assertEquals(findTemplate.name, name);
        assertEquals(findTemplate.user.id, user.id);
    }

}
