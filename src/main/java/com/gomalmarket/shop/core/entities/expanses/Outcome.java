 package com.gomalmarket.shop.core.entities.expanses;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.gomalmarket.shop.core.entities.basic.BaseEntity;
import com.gomalmarket.shop.core.entities.basic.Season;

import java.util.Date;

 @Table(name = "OUTCOMES")
 @Entity(name = "Outcome")
 @Setter
 @Getter
 public class Outcome extends BaseEntity {
	 
		@Id
		@Column(name ="ID" )
		private int id ;
	 
	 
	 
	 
     @Column(name = "Outcome_DATE")
     private Date outcomeDate;

     @Column(name = "TOTAL_OUTCOME")
     private Double totalAmount;

     @ManyToOne
     @JoinColumn(name = "SEASON_ID")
     private Season season;

     @Column(name = "SEASON_ID",insertable = false,updatable = false)
     private int seasonId;
     
 


 	 




 }
