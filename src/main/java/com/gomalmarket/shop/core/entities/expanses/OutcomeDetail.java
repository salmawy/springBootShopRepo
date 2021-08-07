package com.gomalmarket.shop.core.entities.expanses;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.safe.SafeTransaction;

import lombok.Getter;
import lombok.Setter;

@Table(name = "OUTCOME_DETAILS")
@Entity(name = "OutcomeDetail")
@DiscriminatorValue("OUTCOME")
@Setter
@Getter
public class OutcomeDetail extends SafeTransaction    { 
 	
	
	@Column(name = "OUTCOME_TYPE_ID")
	private Integer typeId;
 
	
	
	
	@ManyToOne
	@JoinColumn(name = "FRIDAGE_ID",updatable = false,insertable = false)
	public Fridage fridage;
	
	
	
 	@ManyToOne
	@JoinColumn(name = "OUTCOME_TYPE_ID",updatable = false,insertable = false)
	public OutcomeType outcomeType;
	
	
	
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
