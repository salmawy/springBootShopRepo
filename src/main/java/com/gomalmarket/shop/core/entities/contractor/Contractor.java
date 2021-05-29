package com.gomalmarket.shop.core.entities.contractor;


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

import com.gomalmarket.shop.core.entities.basic.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Table(name ="CONTRACTORS")
@Entity(name ="Contractor")
@Setter
@Getter
public class Contractor extends BaseEntity {
	
	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "CONTRACTOR_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")
	@Id
	@Column(name ="ID" )
	private int  id=-1;

	@Column(name ="NAME" )
	private String name;

	@Column(name ="ADDRESS" )
	private String address;

	@Column(name ="PHONE" )
	private String phone;

	@Column(name ="TYPE_NAME" )
	private String typeName;

	@Column(name ="OWNER_ID" )
	private int ownerId;

	@Column(name ="TYPE_ID" )
	private int typeId;
	
	

	

}
