 package com.gomalmarket.shop.core.entities;


 import lombok.Getter;
 import lombok.Setter;

 import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

 @Table(name = "SELLERS")
 @Entity(name = "Seller")
 @Setter
 @Getter
public class Seller extends BaseEntity {
	 
	 @TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
				pkColumnName = "ID_TABLE_NAME",
				pkColumnValue = "SELLER_ID",
				valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
		@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

		@Id
		@Column(name ="ID" )
		private int id ;
	 

	 @Column(name = "NAME")
	private String name;

	 @Column(name = "ADDRESS")
	private String address;

	 @Column(name = "PHONE")
	private String phone;

	 @Column(name = "TYPE")
	private String  type ;

	 @Column(name = "TYPE_ID")
	private int  typeId ;

	
	
	
	

	public void setTypeId(int typeId) {
		this.typeId = typeId;
		this.type=String.valueOf(typeId);
	}
	
	

	 
}
