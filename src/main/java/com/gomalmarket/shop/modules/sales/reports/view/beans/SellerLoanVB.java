package com.gomalmarket.shop.modules.sales.reports.view.beans;

public class SellerLoanVB {
	int id ;
	private String name;
	double dueAmount;
	double priorLoan;
	double paidAmount;
	double totalOrdersAmount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}
	public double getPriorLoan() {
		return priorLoan;
	}
	public void setPriorLoan(double priorLoan) {
		this.priorLoan = priorLoan;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public double getTotalOrdersAmount() {
		return totalOrdersAmount;
	}
	public void setTotalOrdersAmount(double totalOrdersAmount) {
		this.totalOrdersAmount = totalOrdersAmount;
	}
	
	
	
	

}
