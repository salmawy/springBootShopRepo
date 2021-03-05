package com.gomalmarket.shop.core.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

 
@Entity
@DiscriminatorValue("OUT_LOAN")
@Setter
@Getter
public class OutShopLoan extends ShopLoan {
	
	
	@OneToOne
	@JoinColumn(name = "OUTCOME_DETAIL_ID",insertable = false,updatable = false,nullable = true)
	private OutcomeDetail outcomeDetail;
	
	
	@Column(name = "OUTCOME_DETAIL_ID")
	private Integer outcomeDetailId;
	
	
	

}
