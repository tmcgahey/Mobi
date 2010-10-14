package com.mobi.iou.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

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
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		return getSummary(user.getUserId());
	}

	public ArrayList<SummaryDetails> AddItemReturnSummary(String name,String description, double amount, Date transactionDate) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		Account addToAccount = CreateOrRetrieveAccount(name, user.getUserId());
		addToAccount.addLineItem(description, amount, transactionDate);
        
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			pm.makePersistent(addToAccount);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		
        return getSummary(user.getUserId());
	}
	
	@SuppressWarnings("unchecked")
	private Account CreateOrRetrieveAccount(String accountName, String userID) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Account.class);
		query.setFilter("accountName == accountNameParam && accountOwnerID == accountOwnerIDParam");
		query.declareParameters("String accountNameParam, String accountOwnerIDParam");
		
		List<Account> results;
		
		try {
			 results = (List<Account>) query.execute(accountName,userID);
		} finally {
			query.closeAll();
		}
		
		Account currentAccount = null;
		
		if(results.size() == 1 ) {
			currentAccount = results.get(0);
		} else {
			currentAccount = new Account(accountName, userID);
		}
		
		return currentAccount;
		
	}
	
	
	private ArrayList<SummaryDetails> getSummary(String userID) {
		ArrayList<SummaryDetails> summaryList = new ArrayList<SummaryDetails>();
		
		/*PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Account.class);
		
	    String query = "select from " + AccountLineItem.class.getName();
	    List<AccountLineItem> lineItems = (List<AccountLineItem>) pm.newQuery(query).execute();
	    
	    if (!lineItems.isEmpty()) {
	    	for (AccountLineItem li : lineItems) {
	    		summaryList.add(new SummaryDetails(li.getName(), li.getDescription(), li.getAmount()));
	    	}
	    }
		
	    pm.close();*/
	    
		return summaryList;
		
	}
}
