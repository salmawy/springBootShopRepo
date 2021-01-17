 package com.gomalmarket.shop.core.entities;

 import lombok.Getter;
 import lombok.Setter;

 import javax.persistence.Column;
 import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

 @Table(name = "PRODUCTS")
 @Entity(name = "Product")
 @Setter
 @Getter
public class Product extends  BaseBean  {
	 
	 
	 @GeneratedValue
		@Id
		@Column(name ="ID" )
		private int id ;

	 @Column(name = "NAME")
	private String name;

	 @Column(name = "UNIT")
	private String unit;

	 @Column(name = "COMMISION")
	private Double commision;

	 @Column(name = "UNITE_WEIGHTS")
	private Double uniteWeight;

	
	
}
