package com.gomalmarket.shop.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

@Table(name = "SAFE_OF_DAY")
@Entity(name = "SafeOfDay")
@Setter
@Getter
public class SafeOfDay extends BaseBean {
	
	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "SAFE_OF_DAY_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")
	@Id
	@Column(name ="ID" )
	private int id ;

	 @Column(name = "DAY_DATE")
	 private Date dayDate;
	
    @Column(name = "BALANCE")
    private Double balance;

    @OneToOne
    @JoinColumn(name = "PARENT_ID")
    private SafeOfDay baseAmount;

    @ManyToOne
    @JoinColumn(name = "SEASON_ID")
    private Season season;
    
    @Column(name = "SEASON_ID",insertable = false,updatable = false)
    private Integer seasonId;


}
