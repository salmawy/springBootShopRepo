package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "INCOME_DETAILS")
@Entity(name = "IncomeDetail")
@Setter
@Getter
public class IncomeDetail extends BaseBean {

	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "INCOME_DETAIL_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;
@Column(name = "TYPE_NAME")
    private String typeName;

	@Column(name = "AMOUNT")
    private Double amount;

	@Column(name = "RESIPEINT_NAME")
    private String resipeintName;

	@Column(name = "NOTES")
    private String notes;

	@Column(name = "SELLER_ID")
    private Integer sellerId;


	@Column(name = "SELLER_ORDER_ID")
    private Integer sellerOrderId;

	@ManyToOne
	@JoinColumn(name = "INCOME_ID")
    private Income income;

 
	@Column(name = "INCOME_ID",insertable = false,updatable = false)
    private int incomeId;
	
	
	@Column(name = "INSTALLMENT_ID")
    private Integer installmentId;

	@ManyToOne
	@JoinColumn(name = "TYPE_ID")
    private IncomeType type;

	@ManyToOne
	@JoinColumn(name = "FRIDAGE_ID")
    private Fridage fridage;


}
