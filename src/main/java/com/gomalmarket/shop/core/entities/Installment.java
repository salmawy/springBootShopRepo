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

import java.util.Date;

@Table(name = "INSTALLMENTS")
@Entity(name = "Installment")
@Setter
@Getter
public class Installment extends BaseEntity {

	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "INSTALLMENT_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;
    @Column(name = "INSTALLMENT_DATE")
    private Date instalmentDate;

    @Column(name = "AMOUNT")
    private double amount;

    @Column(name = "SCIENCERE")
    private int sciencere;

    @Column(name = "SELLER_LOAN_BAG_ID")
    private int sellerLoanBagId;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "SELLER_ORDER_ID")
    private Integer sellerOrderId;

	 
}
