package com.mobi.iou.server;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

public class AccountLineItemResource extends ServerResource {

	@SuppressWarnings("unchecked")
	@Get
	public String getAccountLineItems() {
		
		//UserService userService = UserServiceFactory.getUserService();
		//User user = userService.getCurrentUser();
		
		/*PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(AccountLineItemDB.class);
		query.setFilter("accountKey == accountKeyParam");
		query.declareParameters("com.google.appengine.api.datastore.Key accountKeyParam");
			
		Key k = KeyFactory.createKey(AccountDB.class.getSimpleName(), "10");
        //Employee e = pm.getObjectById(Employee.class, k);
		
		List<AccountLineItemDB> accountLineItems = null;
		try {
			accountLineItems = (List<AccountLineItemDB>) query.execute(k);
		} finally {
			query.closeAll();
		}
		
		return Integer.toString(accountLineItems.size());*/
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Key k = KeyFactory.createKey(AccountDB.class.getSimpleName(), "10");
		AccountDB e = pm.getObjectById(AccountDB.class, k);
        
		/*Query temp = pm.newQuery(AccountDB.class);
		temp.setFilter("accountName == accountNameParam");
		temp.declareParameters("String accountNameParam");
		List<AccountDB> accountdb = null;
		try {
			accountdb = (List<AccountDB>) temp.execute("Travis");
		} finally {
			temp.closeAll();
		}*/
		
		
		Key k2;
		Query query = pm.newQuery(AccountLineItemDB.class);
		query.setFilter("accountKey == accountKeyParam");
		query.declareParameters("com.google.appengine.api.datastore.Key accountKeyParam");
		List<AccountLineItemDB> accountLineItems = null;
		try {
			accountLineItems = (List<AccountLineItemDB>) query.execute(e.getKey());
			k2 = accountLineItems.get(0).getAccountKey();
		} finally {
			query.closeAll();
		}
		
		return "0";
	}
	
	@Post
	public Representation addLineItem(String value) {

		JSONObject lineItem = null;

		try {
			JSONObject lineItemJSON = new JSONObject(value);
			lineItem = lineItemJSON.getJSONObject("addLineItem");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		try {
			AccountDB addToAccount = CreateOrRetrieveAccount(lineItem.getString("name"), user.getUserId());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd"); 
			Date convertedDate = dateFormat.parse(lineItem.getString("date")); 
			 
			if(addLineItem(addToAccount, lineItem.getString("description"), lineItem.getDouble("amount"),convertedDate)) {
				setStatus(Status.SUCCESS_CREATED);
			} else {
				setStatus(Status.SERVER_ERROR_INTERNAL);
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Representation rep = new StringRepresentation(lineItem.toString(),
				MediaType.TEXT_PLAIN);
		return rep;
		
	}

	@SuppressWarnings("unchecked")
	private AccountDB CreateOrRetrieveAccount(String accountName, String userID) {

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(AccountDB.class);
		query.setFilter("accountName == accountNameParam && accountOwnerID == accountOwnerIDParam");
		query.declareParameters("String accountNameParam, String accountOwnerIDParam");

		List<AccountDB> results;
		AccountDB currentAccount = null;

		try {
			results = (List<AccountDB>) query.execute(accountName, userID);
			if (results.size() == 1) {
				currentAccount = results.get(0);
			} else {
				currentAccount = new AccountDB(accountName, userID);
				pm.makePersistent(currentAccount);
			}
		} finally {
			query.closeAll();
			pm.close();
		}
		
		return currentAccount;

	}
	
	private boolean addLineItem(AccountDB account, String description,double amount, Date transactionDate) {
		boolean success = true;
		
		account.setAccountSum(account.getAccountSum() + amount);
		AccountLineItemDB lineItem = new AccountLineItemDB(description, amount,transactionDate,account.getKey());
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(lineItem);
			tx.commit();
		} catch(Exception ex) {
			account.setAccountSum(account.getAccountSum() - amount);
			success = false;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		
		PersistenceManager pm2 = PMF.get().getPersistenceManager();
		Transaction tx2 = pm2.currentTransaction();
		try {
			tx2.begin();
			pm2.makePersistent(account);
			tx2.commit();
		} catch(Exception ex) {
			success = false;
		} finally {
			if (tx2.isActive()) {
				tx2.rollback();
			}
			pm2.close();
		}
		
		return success;
	}

}
