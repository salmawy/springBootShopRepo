package com.gomalmarket.shop.modules.Customer.transactions.view.beans;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceViewbean {

	private int id;
	private String invoiceDate;
	private String productName;
	private Double grossWeight;
	private	Double netWeight;
	private	Double nolun;
	private	Double totalAmount;
	private	Double tips;
	private	Double commision;
	private	Double netAmount;
	private	 String orderTag;
	
	
	
	
	
}
