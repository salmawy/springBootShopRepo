package com.gomalmarket.shop.core.entities.sellers;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.basic.Season;

import lombok.Getter;
import lombok.Setter;

@Table(name = "SELLER_ORDERS")
@Entity(name = "SellerOrder")
@Setter
@Getter
public class SellerOrder  {
	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "SELLER_ORDER_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;

    @Column(name = "TOTAL_COST")
    private Double totalCost;

    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Column(name = "NOTES")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "SEASON_ID")
    private Season season;

    @Column(name = "SCIENCERE")
    private int sciencere;

    @Column(name = "SELLER_LOAN_BAG_ID",insertable = false,updatable = false)
    private Integer sellerLoanBagId;
    
   
    @ManyToOne
    @JoinColumn(name = "SELLER_LOAN_BAG_ID",nullable = true)
    private SellerLoanBag sellerLoanBag ;

    @ManyToOne
    @JoinColumn(name = "SELLER_ID")
    private Seller seller;

    @OneToMany(mappedBy = "sellerOrder" ,cascade = {CascadeType.ALL})
    private List<SellerOrderWeight> orderWeights;

    @ManyToOne
    @JoinColumn(name = "FRIDAGE_ID")
    private Fridage fridage;

	 
}
