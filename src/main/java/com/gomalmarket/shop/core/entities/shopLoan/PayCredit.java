package com.gomalmarket.shop.core.entities.shopLoan;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.gomalmarket.shop.core.entities.expanses.IncomeDetail;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("PAY_CREDIT")
@Setter
@Getter
public class PayCredit extends ShopLoanTransaction {
	
	
	@OneToOne
	@JoinColumn(name = "INCOME_DETAIL_ID",insertable = false,updatable = false,nullable = true)
	private IncomeDetail incomeDetail;
	
	
	@Column(name = "INCOME_DETAIL_ID")
	private Integer incomeDetailId;
	
	
	

}