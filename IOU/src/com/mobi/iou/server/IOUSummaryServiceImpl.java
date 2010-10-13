package com.mobi.iou.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
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
		
		addTransaction(name, description, amount);
        
        return getSummary();
	}

	private void addTransaction(String accountName, String description,double amount) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Account.class);
		query.setFilter("accountName == accountNameParam");
		query.declareParameters("String accountNameParam");
		
		try {
			List<Account> results = (List<Account>) query.execute("");
		} finally {
			query.closeAll();
		}
		
		AccountLineItem newItem = new AccountLineItem(accountName,description,amount);
		
		
        try {
            pm.makePersistent(newItem);
        } finally {
            pm.close();
        }
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<SummaryDetails> getSummary() {
		ArrayList<SummaryDetails> summaryList = new ArrayList<SummaryDetails>();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + AccountLineItem.class.getName();
	    List<AccountLineItem> lineItems = (List<AccountLineItem>) pm.newQuery(query).execute();
	    
	    if (!lineItems.isEmpty()) {
	    	for (AccountLineItem li : lineItems) {
	    		summaryList.add(new SummaryDetails(li.getName(), li.getDescription(), li.getAmount()));
	    	}
	    }
		
	    pm.close();
	    
		return summaryList;
		
	}
}
