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
			renderArgs.get("facebookAppid").toString(),
			renderArgs.get("facebookAppsecret").toString()
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
    	if (accessToken == null) {
    		permission();
    		accessToken = session.get("access_token");
    	}
    	JsonObject me = null;
    	me = facebook.getInfo(accessToken);
    	User user = User.find("byFacebook_id", me.get("id").getAsInt()).first();
    	if (user == null) {
    		user = new User(me.get("id").getAsString());
    		user.save();
    	}
    	session.put("id", user.id);
    }

}
