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
 public class Outcome  {
	 
		@Id
		@Column(name ="ID" )
		private int id ;
	 
	 
	 
	 
     @Column(name = "Outcome_DATE")
     private Date outcomeDate;

     @Column(name = "AMOUNT",columnDefinition = " NUMBER(8) ")
     private Double amount;

     @ManyToOne
     @JoinColumn(name = "SEASON_ID")
     private Season season;

     @Column(name = "SEASON_ID",insertable = false,updatable = false)
     private int seasonId;
     
 
   

 	 




 }
