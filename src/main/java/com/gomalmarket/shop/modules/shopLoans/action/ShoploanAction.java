package com.gomalmarket.shop.modules.shopLoans.action;

import java.util.Map;

import com.gomalmarket.shop.core.action.BaseAction;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.shopLoans.services.IShopLoanService;

public class ShoploanAction extends BaseAction {

 	private IShopLoanService shopLoanService;
 	private IBaseService baseService;

 	  public  static  Map<String, Object> request;
 	    public  static  Map<String, Object> response;
   
      
    public ShoploanAction() {
    	
     	setShopLoanService((IShopLoanService)  getSpringBeanFactory().getBean("shopLoanService"));
     	setBaseService((IBaseService)  getSpringBeanFactory().getBean("baseService"));

    
    }


	public IShopLoanService getShopLoanService() {
		return shopLoanService;
	}


	public void setShopLoanService(IShopLoanService shopLoanService) {
		this.shopLoanService = shopLoanService;
	}

 

 

    
    

}
