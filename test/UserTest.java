import org.junit.*;

import play.test.*;
import models.*;

public class UserTest extends UnitTest {

    @Test
    public void create() {
        User user = new User().save();

        User findUser = User.findById(user.id);

        assertEquals(findUser.id, user.id);
    }

}
