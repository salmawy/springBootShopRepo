package com.gomalmarket.shop.core.entities.expanses;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.safe.SafeTransaction;

import lombok.Getter;

@Table(name = "OUTCOME_DETAILS")
@Entity(name = "OutcomeDetail")
@DiscriminatorValue("OUTCOME")
 @Getter
public class OutcomeDetail extends SafeTransaction    { 
 	
	
	
	
	
	
	@ManyToOne
	@JoinColumn(name = "FRIDAGE_ID",updatable = false,insertable = false)
	public Fridage fridage;
	
	
	
	
	
	public void setSpenderName(String name) {
		this.setCreatorName(name);
		
	}
	
	public String  getSpenderName( ) {
		return this.getCreatorName();
		
	}
	
	public Integer getCustomerId() {
	  return this.getPersonId();
}
 
public Integer getCustomerOrderId() {
	  return this.getOrderId();
}


public void setCustomerId(Integer id ) {
	   this.setPersonId(id);
}
 
public void setCustomerOrderId(Integer id ) {
	   this.setOrderId(id);
}}
