package com.mobi.iou.server;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

public class AccountLineItemResource extends ServerResource {

	@Post("json")
	public void addItem() {
		
		Representation entity = getRequestEntity();
        JSONObject jsonobject;
		try {
			jsonobject = new JSONObject(entity.toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}
	
}
