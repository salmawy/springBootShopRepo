package com.gomalmarket.shop.core.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "SAFE_TRACING")
@Entity(name = "SafeTracing")
@Setter
@Getter
public class SafeTracing extends BaseEntity {
	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "SAFE_TRACING_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;
	@ManyToOne
	@JoinColumn(name = "SAFE_ID")
	private SafeOfDay SafeOfDay;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "BEFORE_AMOUNT")
	private double beforAmount;

	@Column(name = "AFTER_AMOUNT")
	private double afterAmount;

	@Column(name = "TRASACTION_TYPE")
	private int transactionType;

	@Column(name = "TRANSACTION_ID")
	private int transactionId;

	@Column(name = "TRNSACTION_NAME")
	private String  transactionName;


	 
	
	
	
}
