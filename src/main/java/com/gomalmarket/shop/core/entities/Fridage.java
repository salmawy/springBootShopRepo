package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name ="FRIDAGES")
@Entity(name ="Fridage")
@Setter
@Getter
public class Fridage  {
	@GeneratedValue
	@Id
	@Column(name ="ID" )
	private int id ;
	@Column(name ="NAME")
	private String name;
	 
}
