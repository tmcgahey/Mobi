package com.mobi.iou.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mobi.iou.client.IOUSummaryService;
import com.mobi.iou.shared.SummaryDetails;

@SuppressWarnings("serial")
public class IOUSummaryServiceImpl extends RemoteServiceServlet implements IOUSummaryService {

	@Override
	public ArrayList<SummaryDetails> getSummaryDetails() {
		
		return getSummary();
	}

	public ArrayList<SummaryDetails> AddItemReturnSummary(String name,String description, double amount) {
		
		SummaryItemData newItem = new SummaryItemData(name,description,amount);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(newItem);
        } finally {
            pm.close();
        }
        
        return getSummary();
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<SummaryDetails> getSummary() {
		ArrayList<SummaryDetails> summaryList = new ArrayList<SummaryDetails>();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + SummaryItemData.class.getName();
	    List<SummaryItemData> lineItems = (List<SummaryItemData>) pm.newQuery(query).execute();
	    
	    if (!lineItems.isEmpty()) {
	    	for (SummaryItemData li : lineItems) {
	    		summaryList.add(new SummaryDetails(li.getName(), li.getDescription(), li.getAmount()));
	    	}
	    }
		
	    pm.close();
	    
		return summaryList;
		
	}
}
