package com.gomalmarket.shop.core.entities.sellers;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.gomalmarket.shop.core.entities.basic.BaseEntity;
import com.gomalmarket.shop.core.entities.basic.Product;
import com.gomalmarket.shop.core.entities.customers.CustomerOrder;

@Table(name = "SELLER_ORDER_WEIGHTS")
@Entity(name = "SellerOrderWeight")
@Setter
@Getter
public class SellerOrderWeight extends BaseEntity {
	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "SELLER_ORDER_WEIGHT_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;

    @Column(name = "GROSS_QUANTITY")
    private Double grossQuantity;

    @Column(name = "NET_QUANTITY")
    private Double netQuantity;

    @Column(name = "UNITE_PRICE")
    private Double unitePrice;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "PACKAGE_NUMBER")
    private int packageNumber;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ORDER_ID",insertable = false,updatable = false)
    private CustomerOrder customerOrder;

    
  
    @Column(name = "CUSTOMER_ORDER_ID",nullable = true)
    private Integer customerOrderId;
    
    
    
     @Column(name = "SELLER_ORDER_ID",insertable = false,updatable = false)
    private int sellerOrderId;
    
     @ManyToOne(cascade = {CascadeType.PERSIST ,CascadeType.MERGE} )
     @JoinColumn(name = "SELLER_ORDER_ID")
     private SellerOrder sellerOrder;
   
     
    
    
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID",insertable = false,updatable = false)
    private Product product;
   
    
    @Column(name = "PRODUCT_ID")
    private Integer productId;
    
}
