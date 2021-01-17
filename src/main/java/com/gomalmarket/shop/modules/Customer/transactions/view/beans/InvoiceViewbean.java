package com.gomalmarket.shop.modules.Customer.transactions.view.beans;

public class InvoiceViewbean {

	int id;
	String invoiceDate;
	String productName;
	private double grossWeight;
	private	double netWeight;
	private	double nolun;
	private	double totalAmount;
	private	double tips;
	private	double commision;
	private	double netAmount;
	private	 String orderTag;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}
	public double getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(double netWeight) {
		this.netWeight = netWeight;
	}
	public double getNolun() {
		return nolun;
	}
	public void setNolun(double nolun) {
		this.nolun = nolun;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getTips() {
		return tips;
	}
	public void setTips(double tips) {
		this.tips = tips;
	}
	public double getCommision() {
		return commision;
	}
	public void setCommision(double commision) {
		this.commision = commision;
	}
	public double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	public String getOrderTag() {
		return orderTag;
	}
	public void setOrderTag(String orderTag) {
		this.orderTag = orderTag;
	}
	
	
	
	
	
	
}
