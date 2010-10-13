package com.mobi.iou.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mobi.iou.shared.LoginInfo;

public interface LoginServiceAsync {

	void login(String requestUri, AsyncCallback<LoginInfo> callback);

}
