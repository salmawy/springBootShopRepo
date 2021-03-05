package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "SHOP_LOANS")
@Entity(name = "ShopLoan")
@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "LOAN_TYPE")
public abstract class ShopLoan extends BaseEntity {

	@TableGenerator(name = "TABLE_GENERATOR", table = "ID_TABLE", pkColumnName = "ID_TABLE_NAME", pkColumnValue = "INC_LOAN_ID", valueColumnName = "ID_TABLE_VALUE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "LOAN_DATE")
	private Date loanDate;

	@Column(name = "AMOUNT")
	private Double amount;

	@ManyToOne
	@JoinColumn(name = "LOAN_ACCOUNT_ID")
	private LoanAccount loanAccount;

	
	@ManyToOne
	@JoinColumn(name = "LOAN_ACCOUNT_ID")
	private Loaner loaner;
	
	@Column(name = "FINISHED")
	private  int finished ;
	
	@Column(name = "NOTES")
	private String notes;

	
	 
}
