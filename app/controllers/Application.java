package controllers;

import play.*;
import play.libs.WS;
import play.mvc.*;

import java.util.*;

import com.google.gson.JsonObject;

import models.*;

public class Application extends Controller {

    public static Facebook facebook = new Facebook(
            "https://graph.facebook.com/oauth/authorize",
            "https://graph.facebook.com/oauth/access_token",
            "144291362341713",
            "da8ad6475b93fc9b6e39e2bdfa1ccdc0"
            //renderArgs.get("facebookAppid").toString(),
            //renderArgs.get("facebookAppsecret").toString()
    );

    public static void index() {
        User user = User.findById(session.get("id"));
        JsonObject me = facebook.getInfo(session.get("access_token").toString());
        render(user, me);
    }

    /**
     * Facebook でのユーザ許可
     */
    public static void permission() {
        if (Facebook.isCodeResponse()) {
            Facebook.Response response = facebook.retrieveAccessToken(play.mvc.Router.getFullUrl("Application.permission"));
            session.put("access_token", response.accessToken);
            //Logger.info(response.error.toString());
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
        String accessToken = session.get("access_token");
        Logger.info(accessToken);
        if (accessToken == null) {
            permission();
            accessToken = session.get("access_token");
        }
        JsonObject me = null;
        me = facebook.getInfo(accessToken);
        User user = User.find("byFacebookId", me.get("id").getAsString()).first();
        if (user == null) {
            user = new User(me.get("id").getAsString());
            user.save();
        }
        session.put("id", user.id);
    }

}
