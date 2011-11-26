package models;

import com.google.gson.JsonObject;

import play.libs.OAuth2;
import play.libs.WS;
import play.mvc.results.Redirect;

public class Facebook extends OAuth2 {

	public Facebook(String authorizationURL, String accessTokenURL,
			String clientid, String secret) {
		super(authorizationURL, accessTokenURL, clientid, secret);
	}

	/**
	 * redirects the user to the authorization page with scope.
	 * @param callbackURL
	 * @param scope
	 */
	public void retrieveVerificationCode(String callbackURL, String scope) {
		throw new Redirect(authorizationURL
				+ "?client_id=" + clientid
				+ "&redirect_uri=" + callbackURL
				+ "&scope=" + scope);
	}
	
	public String getAuthorizationURL() {
		return authorizationURL;
	}
	
	public void setAuthorizationURL(String _autorizationURL) {
		authorizationURL = _autorizationURL;
	}

	public String getClientid() {
		return clientid;
	}
	
	public void setClientid(String _clientid) {
		clientid = _clientid;
	}	
	
    public static JsonObject getInfo(String accessToken) {
    	return WS.url("https://graph.facebook.com/me?access_token=%s", WS.encode(accessToken)).get().getJson().getAsJsonObject();
    }
}
