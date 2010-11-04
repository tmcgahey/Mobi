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
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

public class AccountLineItemResource extends ServerResource {

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
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			AccountDB addToAccount = CreateOrRetrieveAccount(lineItem.getString("name"), user.getUserId(), pm);
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd"); 
			 Date convertedDate = dateFormat.parse(lineItem.getString("date")); 
			 
			addToAccount.addLineItem(lineItem.getString("description"), lineItem.getDouble("amount"),convertedDate);
			
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setStatus(Status.SUCCESS_CREATED);
		Representation rep = new StringRepresentation(lineItem.toString(),
				MediaType.TEXT_PLAIN);
		return rep;
		
	}

	@SuppressWarnings("unchecked")
	private AccountDB CreateOrRetrieveAccount(String accountName, String userID,PersistenceManager pm) {

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
				// pm.makePersistent(currentAccount);
			}
		} finally {
			query.closeAll();
		}

		return currentAccount;

	}

}
