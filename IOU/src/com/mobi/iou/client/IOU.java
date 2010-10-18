package com.mobi.iou.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mobi.iou.server.login.LoginInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IOU implements EntryPoint {

	private LoginJSON loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the Mobi-IOU application.");
	private Anchor signInLink = new Anchor("Sign In");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Check login status using login service.
		
		String url = GWT.getModuleBaseURL() + "login?HostPageBaseURL=" + GWT.getHostPageBaseURL();

		url = URL.encode(url);

	    RequestBuilder loginRequest = new RequestBuilder(RequestBuilder.GET, url);

	    try {
	      @SuppressWarnings("unused")
		Request request = loginRequest.sendRequest(null, new RequestCallback() {
	        public void onError(Request request, Throwable exception) {
	          Window.alert("Couldn't retrieve JSON");
	        }

	        public void onResponseReceived(Request request, Response response) {
	          if (200 == response.getStatusCode()) {
	        	  loginInfo = loginJSONArray(response.getText()).get(0);
        	  	
	        	  if(loginInfo.isLoggedIn()) {
						HandlerManager eventBus = new HandlerManager(null);
						AppController appViewer = new AppController(eventBus,loginInfo.getLogoutUrl());
						appViewer.go(RootPanel.get());
					} else {
						loadLogin();
					}	
	        	  
	          } else {
	            Window.alert("Couldn't retrieve JSON (" + response.getStatusText()
		                + ")");
	          }
	        }
	      });
	    } catch (RequestException e) {
	      Window.alert("Couldn't retrieve JSON");
	    }
	    
	   /* LoginServiceAsync loginService = GWT.create(LoginService.class);
	    loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if(loginInfo.isLoggedIn()) {
					HandlerManager eventBus = new HandlerManager(null);
					AppController appViewer = new AppController(eventBus,loginInfo.getLogoutUrl());
					appViewer.go(RootPanel.get());
				} else {
					loadLogin();
				}			
			}
	    	
	    });*/
	}
	
	private final native JsArray<LoginJSON> loginJSONArray(String json) /*-{
		return eval(json);
	}-*/;
	
	private void loadLogin() {
	    // Assemble login panel.
	    signInLink.setHref(loginInfo.getLoginUrl());
	    loginPanel.add(loginLabel);
	    loginPanel.add(signInLink);
	    RootPanel.get().add(loginPanel);
	  }
	
}
