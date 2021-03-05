package com.gomalmarket.shop.modules.shopLoans.view.beans;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanVB<T> {
	
	private Date date;
	private double amount;
	private String note;
	private T entity;
	

}
