package models;

import com.google.gson.JsonObject;

import play.libs.OAuth2;
import play.libs.WS;
import play.mvc.results.Redirect;

public class Facebook extends OAuth2 {

	public final static String AUTH_URL = "https://graph.facebook.com/oauth/authorize";
	public final static String ACCESS_TOKEN_URL = "https://graph.facebook.com/oauth/access_token";
	public final static String CLIENT_ID = "144291362341713";
	public final static String SECRET = "da8ad6475b93fc9b6e39e2bdfa1ccdc0";

	public Facebook() {
		super(AUTH_URL, ACCESS_TOKEN_URL, CLIENT_ID, SECRET);
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
}
