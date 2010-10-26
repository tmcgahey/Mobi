package com.mobi.iou.server;



import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;



public class AccountLineItemResource extends ServerResource {
	
	@Post
	public Representation addLineItem(Representation entity) {
		
		
        /*JSONObject lineItem;
        
		try {
			JSONObject lineItemJSON = new JSONObject(entity.toString());
			lineItem  = lineItemJSON.getJSONObject("addLineItem");
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
		
		setStatus(Status.SUCCESS_CREATED);  
		Representation rep = new StringRepresentation("Item created", MediaType.TEXT_PLAIN);
		
		/*UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		PersistenceManager pm = PMF.get().getPersistenceManager();*/

		//Account addToAccount = CreateOrRetrieveAccount(lineItem.getString("name"), user.getUserId(),pm);
        return rep;
	}

}
