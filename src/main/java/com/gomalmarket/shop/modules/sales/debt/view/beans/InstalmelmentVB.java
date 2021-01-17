package com.gomalmarket.shop.modules.sales.debt.view.beans;

import java.text.SimpleDateFormat;

public class InstalmelmentVB {
	public static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 

 private int id ;
 private double amount;
 private String instDate;
 private String notes;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public String getInstDate() {
	return instDate;
}
public void setInstDate(String instDate) {
	this.instDate = instDate;
}
public String getNotes() {
	return notes;
}
public void setNotes(String notes) {
	this.notes = notes;
}
	
 
 
 

}
