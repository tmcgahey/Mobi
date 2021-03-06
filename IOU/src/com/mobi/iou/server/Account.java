package com.mobi.iou.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private double accountSum = 0.0;
	
	@Persistent(mappedBy = "account")
	private List<AccountLineItem> lineItems = null;
	
	public Account(String accountName, String accountOwnerID) {
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
	
	public List<AccountLineItem> getLineItems() {
		return lineItems;
	}
	
	public void addLineItem(String description,double amount, Date transactionDate ) {
		accountSum += amount;
		AccountLineItem lineItem = new AccountLineItem(description, amount,transactionDate);
		
		if(lineItems == null) {
			lineItems = new ArrayList<AccountLineItem>();
		}
		
		lineItems.add(lineItem);
	}

	public void setAccountSum(double accountSum) {
		this.accountSum = accountSum;
	}

	public double getAccountSum() {
		return accountSum;
	}

	
	
	
	
	
}
