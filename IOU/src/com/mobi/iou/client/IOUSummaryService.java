package com.mobi.iou.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mobi.iou.shared.SummaryDetails;

@RemoteServiceRelativePath("iouSummary")
public interface IOUSummaryService extends RemoteService {
	
	ArrayList<SummaryDetails> getSummaryDetails();
	ArrayList<SummaryDetails> AddItemReturnSummary(String name,String description, double amount); 

}
