package models;

import javax.mail.Session;

import com.google.gson.JsonObject;

import play.Logger;
import play.libs.OAuth2;
import play.libs.WS;
import play.mvc.results.Redirect;

public class FacebookUser{
	
	public String accessToken;
	public JsonObject json = null;
	
	public FacebookUser(String accessToken) {
		this.accessToken = accessToken;
	}
	
    public String get(String key) {
    	if (this.json == null) {
    		this.json = WS.url("https://graph.facebook.com/me?access_token=%s", WS.encode(this.accessToken)).get().getJson().getAsJsonObject();
    	}
    	return this.json.get(key).toString();
    }
}
