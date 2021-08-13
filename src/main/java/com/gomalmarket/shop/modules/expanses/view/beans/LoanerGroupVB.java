package com.gomalmarket.shop.modules.expanses.view.beans;

import java.util.Date;

import com.gomalmarket.shop.core.Enum.LoanTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanerGroupVB   {
	
	private Date fromDate ;
	private Date toDate;
	private int groupId;
	private int loanerId ;
	private double  amount ;
 	private String loanerName;
	private LoanTypeEnum loanType;
	
	
	
	}
	