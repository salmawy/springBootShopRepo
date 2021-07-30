package com.gomalmarket.shop.core.entities.contractor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name ="CONTRACTOR_ACCOUNTS")
@Entity(name ="ContractorAccount")
@Setter
@Getter
public class ContractorAccount {

	@Id
	@Column(name ="ID" )
	private int id ;

	@Column(name ="TYPE_ID" )
	private int typeId ;
	
	
	@Column(name = "AMOUNT")
	private Double amount;

	 @ManyToOne
	@JoinColumn(name = "CONTRACTOR_ID",updatable = false,insertable = false)
	private  Contractor contractor; 


}