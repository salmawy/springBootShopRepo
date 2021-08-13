package com.gomalmarket.shop.core.entities.shopLoan;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.gomalmarket.shop.core.entities.expanses.OutcomeDetail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

 
@Entity
 @Setter
@Getter
@DiscriminatorValue("LOAN_CREDIT")
 
public class LoanCredit extends ShopLoanTransaction {
	
	@OneToOne
	@JoinColumn(name = "OUTCOME_DETAIL_ID",insertable = false,updatable = false,nullable = true)
	private OutcomeDetail  outcomeDetail;
	
	
	@Column(name = "OUTCOME_DETAIL_ID")
	private Integer outcomeDetailId;


	
	

}