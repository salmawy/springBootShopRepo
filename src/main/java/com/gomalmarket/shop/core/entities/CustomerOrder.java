package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name ="CUSTOMER_ORDERS")
@Entity(name ="CustomerOrder")
@Setter
@Getter
public class CustomerOrder extends BaseBean {

	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "CUSTOMER_ORDER_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;
	@Column(name = "NOLUN")
	private Double nolun;

	@Column(name = "GROSS_WEIGHT")
	private Double grossweight;

	@Column(name ="NET_WEIGHT")
	private Double netWeight;

	@Column(name ="ORDER_DATE")
	private Date orderDate;

	@Column(name ="TOTAL_RPICE")
	private Double totalPrice;

	@Column(name ="NET_PRICE")
	private Double netPrice;

	@Column(name ="EDITE_DATE")
	private Date editeDate;

	@Column(name ="DUE_DATE")
	private Date dueDate;

	@Column(name ="TIPS")
	private Double tips;

	@Column(name ="COMMISSION")
	private Double commision;

	@Column(name ="NOTES")
	private String notes;

	@Column(name ="FINISHED")
	private int finished;

	@Column(name ="DUED")
	private int dued;

	@Column(name ="UNITS")
	private int units;

	@Column(name ="UNITE_PRICE")
	private Double unitePrice;

	@Column(name ="RATIO")
	private Double ratio;

	@Column(name ="BUY_PRICE")
	private Double buyPrice;

	@Column(name ="PERIOD_ID")
	private int periodId = -1;




	@ManyToOne
	@JoinColumn(name ="CUSTOMER_ID")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name ="PRODUCT_ID")
	private Product product;

	@ManyToOne
	@JoinColumn(name ="FRIDAGE_ID")
	private Fridage fridage;

	
	@ManyToOne
	@JoinColumn(name ="SEASON_ID")
	private Season season;
	@ManyToOne
	@JoinColumn(name ="STORE_ID")
	private Store store;


	
	

	@Column(name ="ORDER_TAG")
	private String orderTag;

	@ManyToOne
	@JoinColumn(name ="VECHILE_TYPE_ID")
	private VehicleType vehicleType;


	@Column(name ="INVOICE_STATUS")
	private Integer invoiceStatus;


}
