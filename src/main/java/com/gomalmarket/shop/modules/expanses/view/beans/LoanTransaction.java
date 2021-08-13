package com.gomalmarket.shop.modules.expanses.view.beans;

import java.text.SimpleDateFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTransaction {
	
	private int id;
	private int loanerId;
	private String transactionDate;
	private String notes;
	private String description;
	private double amount;
	public static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 

	
	
	

}
