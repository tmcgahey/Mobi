package com.mobi.iou.server.login;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

public class LoginResource extends ServerResource {

	@Get
	public String login(String requestUri) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo loginInfo = new LoginInfo();

		if (user != null) {
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			loginInfo.setLogoutUrl(userService.createLogoutURL(getQuery().getFirstValue("HostPageBaseURL")));
			
		} else {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(getQuery().getFirstValue("HostPageBaseURL")));
		}
		
		JSONObject loginJSON = new JSONObject(loginInfo);
		JSONArray loginJSONArray = new JSONArray();
		try {
			loginJSONArray.put(0, loginJSON);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return loginJSONArray.toString();
		
	}
	
}
