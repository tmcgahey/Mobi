package com.mobi.iou.client;

import com.google.gwt.core.client.JavaScriptObject;

public class LoginJSON extends JavaScriptObject {
	
	protected LoginJSON() {}
	
	public final native boolean isLoggedIn() /*-{ return this.loggedIn; }-*/;
	public final native String getLoginUrl() /*-{ return this.loginUrl; }-*/;
	public final native String getLogoutUrl() /*-{ return this.logoutUrl; }-*/;
	public final native String getEmailAddress() /*-{ return this.emailAddress; }-*/;
	public final native String getNickname() /*-{ return this.nickname; }-*/;

}
