package com.gomalmarket.shop.core.entities.expanses;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gomalmarket.shop.core.entities.safe.SafeTransaction;

import lombok.Getter;
import lombok.Setter;

@Table(name = "INCOME_DETAILS")
@Entity(name = "IncomeDetail")
@DiscriminatorValue("INCOME")
@Setter
@Getter
public class IncomeDetail extends SafeTransaction  {
	@Column(name = "INSTALLMENT_ID")
	private Integer installmentId;

	
	public void setResipeintName(String name) {
		this.setCreatorName(name);
		
	}
	
	public String  getResipeintName( ) {
		return this.getCreatorName();
		
	}
	public Integer getSellerId() {
		return this.getPersonId();
	}

	public Integer getSellerOrderId() {
		return this.getOrderId();
	}

	public void setSellerId(Integer id) {
		this.setPersonId(id);
	}

	public void setSellerOrderId(Integer id) {
		this.setOrderId(id);
	}
}
