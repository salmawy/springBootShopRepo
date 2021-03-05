package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "LOAN_PAYING")
@Entity(name = "LoanPaying")
@Setter
@Getter
public class LoanPaying extends BaseEntity {


	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "LOAN_PAYING_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;

    @Column(name = "PAID_AMOUNT")
    private Double paidAmunt;

    @Column(name = "PAYING_DATE")
    private Date payingDate;

    @Column(name = "NOTES")
    private String notes;
    @ManyToOne
    @JoinColumn(name = "LOAN_ACCOUNT_ID")
    private LoanAccount loanAccount;

	 
}
