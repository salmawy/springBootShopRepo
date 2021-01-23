 package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

 @Table(name = "OUTCOMES")
 @Entity(name = "Outcome")
 @Setter
 @Getter
 public class Outcome extends BaseBean {
	 
		@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
				pkColumnName = "ID_TABLE_NAME",
				pkColumnValue = "OUTCOME_ID",
				valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
		@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

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
     
     
     
     @ManyToOne
     @JoinColumn(name = "SAEF_ID",nullable = true)
     private SafeOfDay safe;








 }
