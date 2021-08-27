package com.gomalmarket.shop.modules.contractor.view.beans;

import java.text.SimpleDateFormat;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ContractorTransactionVB {

	private int id;
	
	private String date;
	private double amount;
	private String paid ;
	private String notes;
	private String name;
	
	
	
	
	
	public static final SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd"); 

	

	
	
	
	
}
