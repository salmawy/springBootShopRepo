package com.gomalmarket.shop.core.entities.contractor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.gomalmarket.shop.core.entities.basic.BaseEntity;
import com.gomalmarket.shop.core.entities.basic.Season;

import lombok.Getter;
import lombok.Setter;


@Table(name ="CONTRACTOR_ACCOUNT_DETAILS")
@Entity(name ="ContractorAccountDetail")
@Setter
@Getter
public class ContractorAccountDetail extends BaseEntity {

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
