package com.mobi.iou.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONObject;

public class AccountSummaryResource extends ServerResource {

	@SuppressWarnings("unchecked")
	@Get
	public String getAccountSummary() {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Account.class);
		query.setFilter("accountOwnerID == accountOwnerIDParam");
		query.declareParameters("String accountOwnerIDParam");

		List<Account> accounts = null;
		try {
			accounts = (List<Account>) query.execute(user.getUserId());
		} finally {
			query.closeAll();
		}
		
		JSONArray summaryJSONArray = new JSONArray();
		
		if (!accounts.isEmpty()) {
			for (Account a : accounts) {		
				
				SummaryDetails currSummary = new SummaryDetails(a.getAccountName(), "", a.getAccountSum());
				JSONObject summaryJSON = new JSONObject(currSummary);
				summaryJSONArray.put(summaryJSON);
				
				/*if(!a.getLineItems().isEmpty()) {
					
					for (AccountLineItem li : a.getLineItems()) {
			    		summaryList.add(new SummaryDetails(li.getAccount().getAccountName(), li.getDescription(), li.getAmount()));
			    	}
				}*/
			}
		}
	    
		return summaryJSONArray.toString();
		
	}
}
