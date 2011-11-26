package controllers;

import play.*;
import play.libs.WS;
import play.mvc.*;

import java.util.*;

import com.google.gson.JsonObject;

import models.*;

public class Application extends Controller {

    public static Facebook facebook = new Facebook();

    public static void index() {
        User user = User.findById(session.get("id"));
        render(user);
    }

    /**
     * Facebook でのユーザ許可
     */
    public static void permission() {
        if (Facebook.isCodeResponse()) {
            Facebook.Response response = facebook.retrieveAccessToken(play.mvc.Router.getFullUrl("Application.permission"));
            session.put("access_token", response.accessToken.toString());
            index();
        }
        String scope = "email";
        facebook.retrieveVerificationCode(play.mvc.Router.getFullUrl("Application.permission"), scope);
    }
    
    /**
     * Facebook からのデータ取得
     */
    @Before(unless="permission")
    static void checkAuthentication() {
        if (!session.contains("access_token")) {
            permission();
        }
        String accessToken = session.get("access_token");
        FacebookUser fbUser = new FacebookUser(accessToken);
        User user = User.find("byFacebookId", fbUser.get("id")).first();
        if (user == null) {
            user = new User(fbUser.get("id"));
        }
        user.name = fbUser.get("name");
        user.email = fbUser.get("email");
        user.accessToken = accessToken;
        user.save();
        session.put("id", user.id);
    }

}
