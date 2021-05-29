package com.gomalmarket.shop.core.entities.customers;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.gomalmarket.shop.core.entities.basic.BaseEntity;
import com.gomalmarket.shop.core.entities.basic.Season;

import java.util.Date;

@Table(name = "PURCHASED_CUSTMER_INSTS")
@Entity(name = "PurchasedCustomerInst")
@Setter
@Getter
public class PurchasedCustomerInst extends BaseEntity {
	
	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "PURCHASED_CUSTMER_INSTS_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;

	@Column(name = "INSTALLMENT_DATE")
	private Date instalmentDate;

	@Column(name = "AMOUNT")
	private double amount;

	@Column(name = "NOTES")
	private String notes;

	@ManyToOne
	@JoinColumn(name = "SEASON_ID")
	private Season season;

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	@Column(name = "SEASON_ID", insertable = false, updatable = false)
	private int seasonId;

	@Column(name = "CUSTOMER_ID", insertable = false, updatable = false)
	private int customerId;

	
	 
}
