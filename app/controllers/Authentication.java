package controllers;

import play.*;
import play.libs.WS;
import play.mvc.*;

import java.util.*;

import com.google.gson.JsonObject;

import models.*;

public class Authentication extends Controller {

    public static Facebook facebook = new Facebook();

    public static void index() {
        User user = User.findById(session.get("id"));
        List<Mail> mails = Mail.find("user_id = ? order by send_at", user.id).fetch();
        List<Template> templates = Template.find("user_id is null").fetch();
        render(user, mails, templates);
    }
    
    public static void mail(int mailid) {
    	Mail mail = Mail.findById(mailid);
    	render(mail);
    }
    
    public static void template(int templateid) {
    	Template template = Template.findById(templateid);
    	render(template);
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
