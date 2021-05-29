package com.gomalmarket.shop.core.entities.expanses;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gomalmarket.shop.core.entities.basic.BaseEntity;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.safe.SafeOfDay;

import lombok.Getter;
import lombok.Setter;

@Table(name = "INCOMES")
@Entity(name = "Income")
@Setter
@Getter
public class Income extends BaseEntity  {
	
 
	@Id
	@Column(name ="ID" )
	private int id ;
    @Column(name = "AMOUNT")
    private Double totalAmount;

    @Column(name = "INCOME_DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "SEASON_ID")
    private Season season;

    @Column(name = "SEASON_ID",insertable = false,updatable = false)
    private int seasonId;
    
	 
    
    @ManyToOne
    @JoinColumn(name = "SAFE_ID",nullable = true)
    private SafeOfDay safe;


}
