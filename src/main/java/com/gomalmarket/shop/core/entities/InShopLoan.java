package com.gomalmarket.shop.core.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("IN_LOAN")
@Setter
@Getter
public class InShopLoan extends ShopLoan {
	
	
	@OneToOne
	@JoinColumn(name = "INCOME_DETAIL_ID",insertable = false,updatable = false,nullable = true)
	private IncomeDetail incomeDetail;
	
	
	@Column(name = "INCOME_DETAIL_ID")
	private Integer incomeDetailId;
	
	
	

}