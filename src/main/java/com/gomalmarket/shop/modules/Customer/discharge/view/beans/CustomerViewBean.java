/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gomalmarket.shop.modules.Customer.discharge.view.beans;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author ahmed
 */
public class CustomerViewBean {
    
    
    private Integer orderId;
    private String customerName;
    private String customerType;

    private String productName;
    private Double nowlun;
    private Double quantity;
    private Integer count;
    private Double gift;
    private String storeName;
    private int finished;
	private SimpleBooleanProperty chk;
 private String finishedLabel;

 
    public CustomerViewBean(Integer orderId, String customerName, String productName, Double nowlun, Double quantity, Integer count, Double gift, String storeName, int finished) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productName = productName;
        this.nowlun = nowlun;
        this.quantity = quantity;
        this.count = count;
        this.gift = gift;
        this.storeName = storeName;
        this.finished = finished;
		this.chk = new SimpleBooleanProperty(false);

    }

    public CustomerViewBean() {
     this.finished = 0;
		this.chk = new SimpleBooleanProperty(false);


    }

    /**
     * @return the orderId
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the nowlun
     */
    public Double getNowlun() {
        return nowlun;
    }

    /**
     * @param nowlun the nowlun to set
     */
    public void setNowlun(Double nowlun) {
        this.nowlun = nowlun;
    }

    /**
     * @return the quantity
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return the gift
     */
    public Double getGift() {
        return gift;
    }

    /**
     * @param gift the gift to set
     */
    public void setGift(Double gift) {
        this.gift = gift;
    }

    /**
     * @return the storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName the storeName to set
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @return the finished
     */
    public int getFinished() {
        return finished;
    }
  
    /**
     * @param finished the finished to set
     */
    public void setFinished(int finished) {
        this.finished = finished;
    }

	public Boolean getChk() {
		return chk.get();
	}

	public void setChk(boolean chk) {
		this.chk.set(chk);
	}
    
    
	public BooleanProperty chkProperty() {
		return this.chk;
	}

	public String getFinishedLabel() {
		return finishedLabel;
	}

	public void setFinishedLabel(String finishedLabel) {
		this.finishedLabel = finishedLabel;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
    
    
}
