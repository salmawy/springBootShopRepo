package com.gomalmarket.shop.core.entities.safe;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.gomalmarket.shop.core.entities.basic.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Table(name = "SAFE_TRANSACTIONS")
@Entity(name = "SafeTransaction")
@Setter
@Getter
@DiscriminatorColumn(name = "TRANSACTION_TYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class SafeTransaction extends BaseEntity  {

	@TableGenerator(name = "TABLE_GENERATOR", table = "ID_TABLE", pkColumnName = "ID_TABLE_NAME", pkColumnValue = "SAFE_TRANSACTION_ID",
			valueColumnName = "ID_TABLE_VALUE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")
	
	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "TYPE_NAME")
	private String typeName;

	@Column(name = "AMOUNT")
	private Double amount;

	@Column(name = "CREATOR_NAME")
	private String creatorName;

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "TYPE_ID")
	private int typeId;

	@Column(name = "ORDER_ID")
	private Integer orderId;

	@Column(name = "PERSON_ID")
	private Integer personId;
 
	@Column(name = "TRANSACTION_DATE")
	private Date transactionDate;

	@Column(name = "TRANSACTION_DAY")
	private Date transactionDay;
 
	@Column(name = "SEASON_ID")
	private Integer seasonId;
 
	
	@Column(name = "FRIDAGE_ID")
	private Integer fridageId;

}
