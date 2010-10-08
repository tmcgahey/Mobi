package com.mobi.iou.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mobi.iou.shared.SummaryDetails;

public interface IOUSummaryServiceAsync {

	void getSummaryDetails(AsyncCallback<ArrayList<SummaryDetails>> callback);

	void AddItemReturnSummary(String name, String description, double amount,
			AsyncCallback<ArrayList<SummaryDetails>> callback);

}
