package com.gomalmarket.shop.modules.sales.debt.view.beans;

import java.text.SimpleDateFormat;

public class PrifSellerOrderVB {

	private int id;
	private String orderDate;
	private String  fridageName;
	private double totalOrderost;
	
	
	
	
	
	public static final SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd"); 

	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getFridageName() {
		return fridageName;
	}
	public void setFridageName(String fridageName) {
		this.fridageName = fridageName;
	}
	public double getTotalOrderost() {
		return totalOrderost;
	}
	public void setTotalOrderost(double totalOrderost) {
		this.totalOrderost = totalOrderost;
	}
	
}
