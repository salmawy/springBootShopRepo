package com.gomalmarket.shop.core.exception;

public class ErrMsg {
	private String className;
	private String description;
	
	
	public ErrMsg(String className, String description) {
		//super();
		// TODO Auto-generated constructor stub
		this.className = className;
		this.description = description;
	}
	public ErrMsg()
	{
		
	}

	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
