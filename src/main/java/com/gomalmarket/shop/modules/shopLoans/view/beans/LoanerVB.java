package com.gomalmarket.shop.modules.shopLoans.view.beans;

import com.gomalmarket.shop.modules.contractor.view.beans.ContractorVB;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoanerVB<T>  extends RecursiveTreeObject<LoanerVB<T>> {
	
  	
	
	private int id ;
	private StringProperty name;
	private DoubleProperty amount;
	private T entity;
 
	
	

}
