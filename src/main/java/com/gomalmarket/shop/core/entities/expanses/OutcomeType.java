package com.gomalmarket.shop.core.entities.expanses;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gomalmarket.shop.core.entities.basic.BaseEntity;

@Table(name = "OUTCOME_TYPES")
@Entity(name = "OutcomeType")
@Setter
@Getter
public class OutcomeType extends BaseEntity {
	
	
	@GeneratedValue
	@Id
	@Column(name ="ID" )
	private int id ;
	
	
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "NAME_AR")
	private String nameAr;

	
	 
	
	
}
