package com.gomalmarket.shop.core.entities.contractor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gomalmarket.shop.core.entities.basic.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Table(name ="CONTRACTOR_ACCOUNTS")
@Entity(name ="ContractorAccount")
@Setter
@Getter
public class ContractorAccount extends BaseEntity  {

	 
	@Id
	@Column(name ="ID" )
	private int id ;


	@Column(name ="TOTAL_AMOUNT" )
	private Double totalAmount;


	@ManyToOne
	@JoinColumn(name="CONTRACTOR_ID")
	private Contractor contractor;
	
 

	 



}



