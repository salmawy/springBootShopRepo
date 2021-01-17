package com.gomalmarket.shop.modules.billing.view.beans;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceWeight {
	
	private double amount;
	private double unitePrice;
	private double weight;
	
	
	public InvoiceWeight(  double amount,	  double unitePrice,	  double weight) {
	
	this.amount=amount;
	this.unitePrice=unitePrice;
	this.weight=weight;
	
	}
 
	
	

}
