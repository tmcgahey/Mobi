package com.mobi.iou.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mobi.iou.shared.SummaryDetails;

@RemoteServiceRelativePath("iouSummary")
public interface IOUSummary extends RemoteService {
	
	ArrayList<SummaryDetails> getSummaryDetails();
	

}
