package com.mobi.iou.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class AccountLineItemDB {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	private String description;
	private double amount;
	private Date transactionDate;
	private AccountDB account;
	
	public AccountLineItemDB(String description, double amount,Date transactionDate) {
		this.description = description;
		this.amount = amount;
		this.transactionDate = transactionDate;
	}
	
	public Key getKey() {
        return key;
    }
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setAccount(AccountDB account){
		this.account = account;
	}
	
	public AccountDB getAccount() {
		return account;
	}
}
