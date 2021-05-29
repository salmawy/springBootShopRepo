 package com.gomalmarket.shop.core.entities.basic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import java.util.Date;

 @Table(name = "SEASONS")
 @Entity(name = "Season")
 @Setter
 @Getter
 public class Season extends BaseEntity {
	 @TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
				pkColumnName = "ID_TABLE_NAME",
				pkColumnValue = "SEASON_ID",
				valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
		@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

		@Id
		@Column(name ="ID" )
		private int id ;
     @Column(name = "START_DATE")
     private Date startDate;

     @Column(name = "END_DATE")
     private Date endDate;

     @Column(name = "CURRENT_SEASON")
     private int currentSeason;

 	 
 }
