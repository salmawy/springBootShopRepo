package com.gomalmarket.shop.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "SELLER_TYPES")
@Entity(name = "SellerType")
@Setter
@Getter
public class SellerType  extends BaseBean{
	@GeneratedValue
	@Id
	@Column(name ="ID" )
	private int id ;
	@Column(name = "NAME")
	private String name;

}
