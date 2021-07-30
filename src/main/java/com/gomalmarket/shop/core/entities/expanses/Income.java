package com.gomalmarket.shop.core.entities.expanses;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gomalmarket.shop.core.entities.basic.Season;

import lombok.Getter;
import lombok.Setter;

@Table(name = "INCOME")
@Entity(name = "Income")
@Setter
@Getter
public class Income   {
	
 
	@Id
	@Column(name ="ID" )
	private int id ;
    @Column(name = "AMOUNT",columnDefinition = " NUMBER(8) ")
    private Double totalAmount;

    @Column(name = "INCOME_DATE")
    private Date incomeDate;

    @ManyToOne
    @JoinColumn(name = "SEASON_ID")
    private Season season;

    @Column(name = "SEASON_ID",insertable = false,updatable = false)
    private int seasonId;
    
    
   
	 
    
    
}
