package com.gomalmarket.shop.modules.expanses.view.beans;

import java.util.Date;

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
	private Date transactionDate;
	private String notes;
	private String description;
	private double amount;
	
	
	
	

}
