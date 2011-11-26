import org.junit.*;

import play.test.*;
import models.*;

public class UserTest extends UnitTest {

    @Test
    public void create() {
        User user = new User("facebookId").save();

        User findUser = User.findById(user.id);

        assertEquals(findUser.id, user.id);
    }

    @Test
    public void email() {
        User user = new User("facebookId").save();

        assertEquals("example@example.com", user.email());
    }

}
