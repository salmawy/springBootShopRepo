package com.gomalmarket.shop.core.entities.shopLoan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Table(name = "SHOP_LOANS_TRANSACTIONS")
@Entity(name = "ShopLoan")
@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TRANSACTION_TYPE")
 
public class ShopLoanTransaction {

	@TableGenerator(name = "TABLE_GENERATOR", table = "ID_TABLE", pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "SHOP_LOANS_TRANSACTIONS_ID", valueColumnName = "ID_TABLE_VALUE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "TRANSACTION_DATE")
	private Date transactionDate;

	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "LOANER_ID")
	private int loanerId;
	
	
	@Column(name = "FINISHED")
	private  int finished ;
	
	@Column(name = "GROUP_ID")
	private  int groupId ;
	
	@Column(name = "NOTES")
	private String notes;
	
	
	@ManyToOne
	@JoinColumn(name = "LOANER_ID",insertable = false,updatable = false)
	private Loaner loaner;
	
}
