package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "SELLER_LOAN_BAGS")
@Entity(name = "SellerLoanBag")
@Setter
@Getter
public class SellerLoanBag extends BaseBean {
	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "SELLER_LOAN_BAG_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")

	@Id
	@Column(name ="ID" )
	private int id ;

    @Column(name = "PRIOR_LOAN")
    private double priorLoan;

    @Column(name = "CURRENT_LOAN")
    private double currentLoan;

    @Column(name = "DUE_LOAN")
    private double dueLoan;

    @Column(name = "PAID_AMOUNT")
    private double paidAmount;

    @Column(name = "NOTES")
    private String notes;


    @ManyToOne
    @JoinColumn(name = "SELLER_ID")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "SEASON_ID")
    private Season season;


    public SellerLoanBag() {
        this.currentLoan = 0.0;
        this.priorLoan = 0.0;
        this.dueLoan = 0.0;
        this.paidAmount = 0.0;


    }

}



