package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "SAFE")
@Entity(name = "Safe")
@Setter
@Getter
public class Safe extends BaseBean {
	
	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "SAFE_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")
	@Id
	@Column(name ="ID" )
	private int id ;

    @Column(name = "BALANCE")
    private Double balance;

    

 


}
