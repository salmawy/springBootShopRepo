package com.gomalmarket.shop.core.entities.shopLoan;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gomalmarket.shop.core.entities.basic.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Table(name = "LOANERS")
@Entity(name = "Loaner")
@Setter
@Getter
public class Loaner extends BaseEntity  {
	
	
	@Id
	@Column(name ="ID" )
	private int id ;
	
    @Column(name = "NAME")
    private String name;

    @Column(name = "LOAN_ACCOUNT_ID")
    private int loanAccountId;

	 


}
