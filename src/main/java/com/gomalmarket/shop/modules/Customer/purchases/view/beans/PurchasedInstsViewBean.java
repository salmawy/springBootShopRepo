package com.gomalmarket.shop.modules.Customer.purchases.view.beans;

import java.text.SimpleDateFormat;

public class PurchasedInstsViewBean {
	int id ;
	String date ;
	double amount;
	String notes;
	
	public static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	

}
