package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name ="INC_LOANS")
@Entity(name ="IncLoan")
@Setter
@Getter
public class IncLoan extends BaseBean {
	
	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "INC_LOAN_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;
	
	
@Column(name ="LOAN_DATE")
	private Date loanDate;

	@Column(name ="AMOUNT")
	private Double amount;

 	@ManyToOne
	@JoinColumn(name ="LOAN_ACCOUNT_ID")
	private LoanAccount loanAccount;

	@Column(name ="NOTES")
	private String notes;







}
