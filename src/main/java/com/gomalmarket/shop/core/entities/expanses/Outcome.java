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

 @Table(name = "OUTCOME")
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
