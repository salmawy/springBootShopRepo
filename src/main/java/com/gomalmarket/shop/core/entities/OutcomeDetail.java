package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "OUTCOME_DETAILS")
@Entity(name = "OutcomeDetail")
@Setter
@Getter
public class OutcomeDetail extends BaseBean {

	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "OUTCOME_DETAIL_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;
	
	
	
    @Column(name = "TYPE_NAME")
    private String typeName;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "SPENDER_NAME")
    private String spenderName;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "CUSTOMER_ID")
    private Integer customerId;

    @Column(name = "ORDER_ID")
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "FRIDAGE_ID")
    private Fridage fridage;

    @ManyToOne
    @JoinColumn(name = "OUTCOME_ID")
    private Outcome outcome;

    
    @Column(name = "OUTCOME_ID",insertable = false,updatable = false)
    private int outcomeId;
    
    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    private OutcomeType type;


}
