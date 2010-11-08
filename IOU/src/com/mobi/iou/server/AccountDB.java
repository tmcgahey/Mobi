package com.mobi.iou.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class AccountDB {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	private String accountName;
	private String accountOwnerID;
	private double accountSum = 0.0;
	
	public AccountDB(String accountName, String accountOwnerID) {
		this.accountName = accountName;
		this.accountOwnerID = accountOwnerID;
		accountSum = 0.0;
	}
	
	public Key getKey() {
        return key;
    }
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountOwnerID() {
		return accountOwnerID;
	}

	public void setAccountOwnerID(String userID) {
		this.accountOwnerID = userID;
	}

	public void setAccountSum(double accountSum) {
		this.accountSum = accountSum;
	}

	public double getAccountSum() {
		return accountSum;
	}

	
	
	
	
	
}
