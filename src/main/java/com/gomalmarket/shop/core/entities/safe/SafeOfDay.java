package com.gomalmarket.shop.core.entities.safe;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gomalmarket.shop.core.entities.basic.BaseEntity;
import com.gomalmarket.shop.core.entities.basic.Season;

import lombok.Getter;
import lombok.Setter;

@Table(name = "SAFE_OF_DAY")
@Entity(name = "SafeOfDay")
@Setter
@Getter
public class SafeOfDay  {
	
	
	@Id
	@Column(name ="ID" )
	private int id ;

	 @Column(name = "DAY_DATE")
	 private Date dayDate;
	
    @Column(name = "BALANCE",columnDefinition = " NUMBER(8) ")
    private Double balance;



    @ManyToOne
    @JoinColumn(name = "SEASON_ID")
    private Season season;
    
    @Column(name = "SEASON_ID",insertable = false,updatable = false)
    private Integer seasonId;

	 
}
