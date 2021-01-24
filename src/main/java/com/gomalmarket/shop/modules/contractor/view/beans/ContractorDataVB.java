package com.gomalmarket.shop.modules.contractor.view.beans;

import java.text.SimpleDateFormat;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ContractorDataVB {

	private int id;
	
	private String date;
	private double amount;
	private boolean paid ;
	private String notes;
	private String name;
	
	
	
	
	
	public static final SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd"); 

	
	
	
	
	
	
	
	
	
	
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

 
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public double getAmount() {
		return amount;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public BooleanProperty chkProperty() {
		return new SimpleBooleanProperty(paid);
	}
	
	
	
	
	
	
	
}
