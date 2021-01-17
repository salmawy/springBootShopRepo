package com.gomalmarket.shop.modules.Customer.purchases.view.beans;

import java.text.SimpleDateFormat;

public class PurchasedInvoicesVB {

	int id;
	String invoiceDate;
	String vehicelType;
	double grossWeight;
	double netWeight;
	double nolun;
	double totalAmount;
	double unitePrice;
	double tips;
	double commision;
	double buyPrice;
	public static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 

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
	
	public String getVehicelType() {
		return vehicelType;
	}
	public void setVehicelType(String vehicelType) {
		this.vehicelType = vehicelType;
	}
	public double getUnitePrice() {
		return unitePrice;
	}
	public void setUnitePrice(double unitePrice) {
		this.unitePrice = unitePrice;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
}