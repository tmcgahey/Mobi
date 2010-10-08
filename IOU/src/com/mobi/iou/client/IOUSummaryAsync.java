package com.mobi.iou.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mobi.iou.shared.SummaryDetails;

public interface IOUSummaryAsync {

	void getSummaryDetails(AsyncCallback<ArrayList<SummaryDetails>> callback);

}
