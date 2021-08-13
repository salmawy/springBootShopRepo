package com.gomalmarket.shop.core.entities.shopLoan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "SHOP_LOAN_GROUPS")
@Entity(name = "LoanGroup")
@Setter
@Getter
public class LoanGroup {

	@Id
	@Column(name = "GROUP_ID")
	private int id;

	@Column(name = "FROM_DATE")
	private Date fromDate;

	@Column(name = "TO_DATE")
	private Date toDate;	
	
	@Column(name = "LOANER_ID")
	private int loanerId ;

	@Column(name = "AMOUNT")
	private double amount;  

	@Column(name = "LOANER_NAME")
	private String loanerName;

	@Column(name = "LOAN_TYPE")
	private String loanType;

}
