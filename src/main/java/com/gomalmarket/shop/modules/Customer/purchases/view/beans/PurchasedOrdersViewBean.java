package com.gomalmarket.shop.modules.Customer.purchases.view.beans;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class PurchasedOrdersViewBean {
	private int id ;
	private SimpleStringProperty date;
	private SimpleDoubleProperty grossWeight;
	private SimpleDoubleProperty nolun;
	private SimpleDoubleProperty unitePrice;
	private SimpleDoubleProperty buyPrice;
	 static final SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD"); 
	 private boolean edited;
	
	public PurchasedOrdersViewBean() {
		this.date = new SimpleStringProperty();
		this.grossWeight = new SimpleDoubleProperty( );
		this.nolun = new SimpleDoubleProperty( );
		this.unitePrice = new SimpleDoubleProperty( );
		this.buyPrice = new SimpleDoubleProperty();

 
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date.get();
	}
	public void setDate(String date) {
		this.date.set(date);
	}
	public double getGrossWeight() {
		return grossWeight.get();
	}
	public void setGrossWeight(double grossWeight) {
		this.grossWeight.set(grossWeight);
	}
	public double getNolun() {
		return nolun.get();
	}
	public void setNolun(double nolun) {
		this.nolun.set(nolun);
	}
	public double getUnitePrice() {
		return unitePrice.get();
	}
	public void setUnitePrice(double unitePrice) {
		this.unitePrice.set(unitePrice);
	}
	public double getBuyPrice() {
		return buyPrice.get();
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice.set(buyPrice);
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}
	

}
