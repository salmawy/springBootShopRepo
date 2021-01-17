package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Table(name ="CONTRACTOR_ACCOUNT_DETAILS")
@Entity(name ="ContractorAccountDetail")
@Setter
@Getter
public class ContractorAccountDetail extends BaseBean {

	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "CONTRACTOR_ACCOUNT_DETAIL_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;

	@Column(name = "DETAIL_DATE")
	private Date detailDate;

	@Column(name = "AMOUNT")
	private Double amount;

	@Column(name = "REPORT")
	private String report;

	@Column(name = "SPENDER_NAME")
	private String spenderName ;

	@Column(name = "PAID")
	private int paid;

	@ManyToOne
	@JoinColumn(name = "SEASON_ID")
	private Season season;

	@ManyToOne
	@JoinColumn(name = "CONTRACTOR_ACCOUNT_ID")
	private  ContractorAccount contractorAccount;
	
	

   


}
