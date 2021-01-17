package com.gomalmarket.shop.modules.sales.debt.view.beans;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class SellerDebtVB extends  RecursiveTreeObject<SellerDebtVB>  {

	private int id ;
	private StringProperty sellerName;
	private DoubleProperty dueAmount;
	private DoubleProperty totalOrdersCost;
	
	
	
	
	
	
	
	
	@Override 
	public ObservableList<SellerDebtVB> getChildren() {
		// TODO Auto-generated method stub
		return super.getChildren();
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public StringProperty getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName =new SimpleStringProperty(sellerName);
	}
	public DoubleProperty getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(double dueAmount) {
		this.dueAmount = new SimpleDoubleProperty(dueAmount);
	}
	public DoubleProperty getTotalOrdersCost() {
		return totalOrdersCost;
	}
	public void setTotalOrdersCost(double totalOrdersCost) {
		this.totalOrdersCost =new SimpleDoubleProperty(totalOrdersCost);
	}


	public void setSellerName(StringProperty sellerName) {
		this.sellerName = sellerName;
	}


	public void setDueAmount(DoubleProperty dueAmount) {
		this.dueAmount = dueAmount;
	}


	public void setTotalOrdersCost(DoubleProperty totalOrdersCost) {
		this.totalOrdersCost = totalOrdersCost;
	}



	
	//======================================

	
	
	
	
	
	
}
