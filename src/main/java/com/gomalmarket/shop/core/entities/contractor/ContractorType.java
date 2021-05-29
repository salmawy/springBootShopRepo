package com.gomalmarket.shop.core.entities.contractor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "CONTRACTORS_TYPES")
@Entity(name="ContractorType")

@Setter
@Getter
public class ContractorType {
	
	
	@Id
	public int id;
	
	@Column(name = "name")
	public String name;
	
	
	

}
