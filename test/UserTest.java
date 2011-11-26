import org.junit.*;

import java.util.List;
import play.test.*;
import models.*;

public class UserTest extends UnitTest {

    @Test
    public void create() {
    	String facebookAccessToken = "my facebook access token";
    	User user = new User(facebookAccessToken);
    	user.save();
    	
    	User findUser = User.findById(user);
    	
    	assertEquals(findUser.facebookAccessToken, facebookAccessToken);
    }

}
