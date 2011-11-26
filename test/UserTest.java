import org.junit.*;

import java.util.List;
import play.test.*;
import models.*;

public class UserTest extends UnitTest {

    @Test
    public void create() {
    	String facebookAccessToken = "test facebook access token";
    	new User(facebookAccessToken).save();
    }

}
