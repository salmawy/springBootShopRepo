 package com.gomalmarket.shop.core.entities;

 import lombok.Getter;
 import lombok.Setter;

 import javax.persistence.Column;
 import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

 @Table(name = "STORES")
 @Entity(name = "Store")
 @Setter
 @Getter
public class Store extends BaseBean {
	 @GeneratedValue
		@Id
		@Column(name ="ID" )
		private int id ;
	 @Column(name = "FRIDAGE_ID")
	private Integer fridageId;



	
}
