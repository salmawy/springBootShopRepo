package com.gomalmarket.shop.modules.contractor.view.beans;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Builder;

 
 
public class ContractorVB  extends RecursiveTreeObject<ContractorVB>  {
	
	private int contractorId ;
	private StringProperty name;
	private DoubleProperty amount;
	private String accountId;
	
	
 
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
	
	public void setAmount(double amount) {
		this.amount = new SimpleDoubleProperty(amount);
	}	
	
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	public int getContractorId() {
		return contractorId;
	}
	public void setContractorId(int contractorId) {
		this.contractorId = contractorId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public ContractorVB() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

	
	
	
}
