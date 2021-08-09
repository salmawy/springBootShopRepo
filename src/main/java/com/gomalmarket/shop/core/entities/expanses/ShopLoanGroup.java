package com.gomalmarket.shop.core.entities.expanses;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gomalmarket.shop.core.entities.shopLoan.Loaner;

import lombok.Getter;
import lombok.Setter;

@Table(name = "SHOP_LOAN_GROUPS")
@Entity(name = "ShopLoanGroup")
@Setter
@Getter
public class ShopLoanGroup {

	@Id
	@Column(name = "GROUP_ID")
	private int id;

	@Column(name = "FROM_DATE")
	private Date fromDate;

	@Column(name = "TO_DATE")
	private Date toDate;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "LOAN_TYPE")
	private String loanType;

	@ManyToOne
	@JoinColumn(name = "LOANER_ID")
	private Loaner loaner;

}
