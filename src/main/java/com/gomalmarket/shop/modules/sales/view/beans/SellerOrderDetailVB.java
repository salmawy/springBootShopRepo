package com.gomalmarket.shop.modules.sales.view.beans;

import org.exolab.castor.dsml.Producer;

import com.gomalmarket.shop.core.entities.CustomerOrder;
import com.gomalmarket.shop.core.entities.Product;
import com.gomalmarket.shop.core.entities.SellerOrderWeight;

import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class SellerOrderDetailVB {
	
 private int customerOrderId;
 private int productId;
 private int sellerWeightId=0;
 private String productName;
 private int count;
 private double grossWeight;
 private double netWeight;
 private double unitePrice;
 private double amount;
 private String customerOrderName;
 
 
 private CustomerOrder customerOrder;
 private Product product;
 private SellerOrderWeight orderWeight;

 

}
