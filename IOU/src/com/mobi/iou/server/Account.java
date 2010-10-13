package com.mobi.iou.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Account {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	private String accountName;
	private String accountOwnerID;
	
	
	public Key getKey() {
        return key;
    }
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getaccountOwnerID() {
		return accountOwnerID;
	}

	public void setUserID(String userID) {
		this.accountOwnerID = userID;
	}

	
	
	
	
	
}
