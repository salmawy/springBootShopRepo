package com.gomalmarket.shop.modules.expanses.view.beans;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoanersNameVB extends RecursiveTreeObject<LoanersNameVB>  {
	
	private int id ;
	private StringProperty name ;
	private DoubleProperty  amount ;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	public void setAmount(double amount) {
		this.amount = new SimpleDoubleProperty(amount);
	}
	public StringProperty getName() {
		return name;
	}
	public void setName(StringProperty name) {
		this.name = name;
	}
	public DoubleProperty getAmount() {
		return amount;
	}
	public void setAmount(DoubleProperty amount) {
		this.amount = amount;
	}
	
	

}
