package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "INCOMES")
@Entity(name = "Income")
@Setter
@Getter
public class Income extends BaseEntity  {
	
	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "INCOME_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;
    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

    @Column(name = "INCOME_DATE")
    private Date incomeDate;

    @ManyToOne
    @JoinColumn(name = "SEASON_ID")
    private Season season;

    @Column(name = "SEASON_ID",insertable = false,updatable = false)
    private int seasonId;
    
	@Embedded
	private BaseEntity systemColumns;
    
    
    @ManyToOne
    @JoinColumn(name = "SAFE_ID",nullable = true)
    private SafeOfDay safe;


}
