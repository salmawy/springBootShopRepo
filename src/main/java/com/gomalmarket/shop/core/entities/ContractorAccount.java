package com.gomalmarket.shop.core.entities;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

@Table(name ="CONTRACTOR_ACCOUNTS")
@Entity(name ="ContractorAccount")
@Setter
@Getter
public class ContractorAccount extends BaseEntity  {

	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "CONTRACTOR_ACCOUNT_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;


	@Column(name ="TOTAL_AMOUNT" )
	private Double totalAmount;


	@ManyToOne
	@JoinColumn(name="CONTRACTOR_ID")
	private Contractor contractor;
	
 

	 



}



