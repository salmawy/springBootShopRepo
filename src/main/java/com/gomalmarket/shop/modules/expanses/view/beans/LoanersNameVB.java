package com.gomalmarket.shop.modules.expanses.view.beans;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoanersNameVB extends RecursiveTreeObject<LoanersNameVB>  {
	
	private int id ;
	private StringProperty name ;
	private DoubleProperty  amount ;
	private int  loanerId ;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
	//name setter and getter
	public StringProperty getName() {
		return name;
	}
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}		
	public void setName(StringProperty name) {
		this.name = name;
	}
	
	
	//amount setter and getter
	public DoubleProperty getAmount() {
		return amount;
	}	
	public void setAmount(DoubleProperty amount) {
		this.amount = amount;
	}
	public void setAmount(double amount) {
		this.amount = new SimpleDoubleProperty(amount);
	}
	
	
	//loanerId setter and getter
 
	public int getLoanerId() {
		return loanerId;
	}
	public void setLoanerId(int loanerId) {
		this.loanerId = loanerId;
	}
}
