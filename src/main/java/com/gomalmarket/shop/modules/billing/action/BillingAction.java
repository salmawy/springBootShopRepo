package com.gomalmarket.shop.modules.billing.action;

import java.util.Map;

import com.gomalmarket.shop.core.action.BaseAction;
import com.gomalmarket.shop.modules.Customer.service.ICustomerService;
import com.gomalmarket.shop.modules.billing.services.IBillingService;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;

public class BillingAction extends BaseAction {

	private IBillingService billingService;
	private IExpansesServices expansesService;
	private ICustomerService customerService;
	
    public  static  Map<String, Object> request;
    public  static  Map<String, Object> response;
   
      
    public BillingAction() {
    	
    	setBillingService((IBillingService) getSpringBeanFactory().getBean("billingService"));
    	setExpansesService((IExpansesServices)  getSpringBeanFactory().getBean("expansesServices"));
    	setCustomerService((ICustomerService)  getSpringBeanFactory().getBean("customerService"));

    
    }
	 
	public IBillingService getBillingService() {
		return billingService;
	}
	public void setBillingService(IBillingService billingService) {
		this.billingService = billingService;
	}

	public IExpansesServices getExpansesService() {
		return expansesService;
	}

	public void setExpansesService(IExpansesServices expansesService) {
		this.expansesService = expansesService;
	}

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

 


    
    

}
