package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "LOAN_ACCOUNTS")
@Entity(name = "LoanAccount")
@Setter
@Getter
public class LoanAccount extends BaseEntity {

	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "LOAN_ACCOUNT_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;
	@Column(name = "TYPE")
    private String type;

	@Column(name = "DUE_AMOUNT")
    private Double dueAmount;

	@Column(name = "FINISHED")
    private int finished;

	@Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

	@ManyToOne
	@JoinColumn(name = "LOANER_ID")
    private Loaner loaner;

    public LoanAccount() {
        dueAmount = 0.0;
        finished = 0;
        totalAmount = 0.0;

    }

 }
