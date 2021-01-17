package com.gomalmarket.shop.modules.Customer.transactions.view.beans;

public class CustomerNameViewBean {
	int id;
	String name;
	String type;
	
	
	
	public CustomerNameViewBean(int id , String name ,String type) {
		this.id=id;
		this.name=name;
		this.type=type;
	}
	
	
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	

}
