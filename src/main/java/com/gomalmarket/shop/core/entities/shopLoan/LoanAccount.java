package com.gomalmarket.shop.core.entities.shopLoan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "LOAN_ACCOUNTS")
@Entity(name = "LoanAccount")
@Setter
@Getter
public class LoanAccount  {
 	@Id
	@Column(name = "LOANER_ID")
	private int id;	

	@Column(name = "TYPE")
    private String type;

	@Column(name = "AMOUNT")
    private double amount;

	 
	@Column(name = "NAME")
    private String  name;
	 
	@ManyToOne
	@JoinColumn(name = "LOANER_ID",insertable = false,updatable = false)
    private Loaner loaner;

	 

}
