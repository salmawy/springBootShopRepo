package com.gomalmarket.shop.modules.sales.view.beans;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SellerOrderVB {
	
	private SimpleBooleanProperty chk;
	private int id;
	private String sellerName;
	private String sellerType;
	private double totalAmount;
	private double paidAmount;
	
	public SellerOrderVB() {
		this.chk = new SimpleBooleanProperty(false);
	}
	
	
	public Boolean getChk() {
		return chk.get();
	}

	public void setChk(boolean chk) {
		this.chk.set(chk);
	}
    
    
	public BooleanProperty chkProperty() {
		return this.chk;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	
	
	

}
