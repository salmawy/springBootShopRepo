package com.gomalmarket.shop.modules.sales.action;

import java.util.Map;

import com.gomalmarket.shop.core.action.BaseAction;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;
import com.gomalmarket.shop.modules.inventory.spring.IInventoryService;
import com.gomalmarket.shop.modules.sales.service.ISalesService;

public class SalesAction extends BaseAction {
	
	private IExpansesServices expansesService;

	private ISalesService salesService;
	 public  static  Map<String, Object> orderDataMap;
	private IInventoryService inventoryService;
      public  static  Map<String, Object> request;
     public  static  Map<String, Object> response;
     public static final int CashId=200;

	public SalesAction() {
 
		salesService= (ISalesService) getSpringBeanFactory().getBean("salesService"); 
		expansesService= (IExpansesServices) getSpringBeanFactory().getBean("expansesServices"); 
		inventoryService= (IInventoryService) getSpringBeanFactory().getBean("inventoryService"); 

	
		
		
		
	}
	public ISalesService getSalesService() {
		return salesService;
	}
	public void setSalesService(ISalesService salesService) {
		this.salesService = salesService;
	}
	 
	public IExpansesServices getExpansesService() {
		return expansesService;
	}
	public IInventoryService getInventoryService() {
		return inventoryService;
	}
	public void setInventoryService(IInventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}
	
	public Map<String, Object> getOrderDataMap() {
		return orderDataMap;
	}
	public void setOrderDataMap(Map<String, Object> orderDataMap) {
		this.orderDataMap = orderDataMap;
	}
	

}
