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
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Account addToAccount = CreateOrRetrieveAccount(name, user.getUserId(),pm);
		addToAccount.addLineItem(description, amount, transactionDate);
		
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			pm.makePersistent(addToAccount);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		
        return getSummary(user.getUserId());
	}
	
	@SuppressWarnings("unchecked")
	private Account CreateOrRetrieveAccount(String accountName, String userID, PersistenceManager pm) {
		
		Query query = pm.newQuery(Account.class);
		query.setFilter("accountName == accountNameParam && accountOwnerID == accountOwnerIDParam");
		query.declareParameters("String accountNameParam, String accountOwnerIDParam");
		
		List<Account> results;
		Account currentAccount = null;
		
		try {
			 results = (List<Account>) query.execute(accountName,userID);
			 if(results.size() == 1 ) {
				 currentAccount = results.get(0);
			 } else {
				 currentAccount = new Account(accountName, userID);
				 //pm.makePersistent(currentAccount);
			 }
		} finally {
			query.closeAll();
		}
		
		return currentAccount;
		
	}
	
	
	@SuppressWarnings("unchecked")
	private ArrayList<SummaryDetails> getSummary(String userID) {
		ArrayList<SummaryDetails> summaryList = new ArrayList<SummaryDetails>();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Account.class);
		query.setFilter("accountOwnerID == accountOwnerIDParam");
		query.declareParameters("String accountOwnerIDParam");

		List<Account> accounts = null;
		try {
			accounts = (List<Account>) query.execute(userID);
		} finally {
			query.closeAll();
		}
		
		if (!accounts.isEmpty()) {
			for (Account a : accounts) {				
				
				summaryList.add(new SummaryDetails(a.getAccountName(), "", a.getAccountSum()));
				
				/*if(!a.getLineItems().isEmpty()) {
					
					for (AccountLineItem li : a.getLineItems()) {
			    		summaryList.add(new SummaryDetails(li.getAccount().getAccountName(), li.getDescription(), li.getAmount()));
			    	}
				}*/
			}
		}
	    
		return summaryList;
		
	}
}
