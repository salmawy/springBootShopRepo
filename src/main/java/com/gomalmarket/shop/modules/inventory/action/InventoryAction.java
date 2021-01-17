package com.gomalmarket.shop.modules.inventory.action;

import java.util.HashMap;
import java.util.Map;

import com.gomalmarket.shop.core.action.BaseAction;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;
import com.gomalmarket.shop.modules.sales.service.ISalesService;

public class InventoryAction extends BaseAction {
	
	private IExpansesServices expansesService;

	private ISalesService salesService;
	
     public  static  Map<String, Object> orderDataMap;
     public  static  Map<String, Object> request;
     public  static  Map<String, Object> response;
     public static final int CashId=200;

	public InventoryAction() {
		orderDataMap =new  HashMap<String, Object> ();
	//	request =new  HashMap<String, Object> ();
	//	response =new  HashMap<String, Object> ();

		salesService= (ISalesService) getSpringBeanFactory().getBean("salesService"); 
		expansesService= (IExpansesServices) getSpringBeanFactory().getBean("expansesServices"); 

		
		
		
	}
	public ISalesService getSalesService() {
		return salesService;
	}
	public void setSalesService(ISalesService salesService) {
		this.salesService = salesService;
	}
	public Map<String, Object> getOrderDataMap() {
		return orderDataMap;
	}
	public void setOrderDataMap(Map<String, Object> orderDataMap) {
		this.orderDataMap = orderDataMap;
	}
	public IExpansesServices getExpansesService() {
		return expansesService;
	}
	

	

}
