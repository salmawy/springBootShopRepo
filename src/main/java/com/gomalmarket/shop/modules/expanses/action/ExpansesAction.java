package com.gomalmarket.shop.modules.expanses.action;

import java.util.Map;

import org.springframework.beans.BeansException;

import com.gomalmarket.shop.core.action.BaseAction;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;

public class ExpansesAction  extends BaseAction{
	
	
	
	private IExpansesServices expansesServices;
	
    public  static  Map<String, Object> request;
    public  static  Map<String, Object> response;

	public ExpansesAction() {
		
		try {
			expansesServices= (IExpansesServices) getSpringBeanFactory().getBean("expansesServices");
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		
		
	}

	public IExpansesServices getExpansesServices() {
		return expansesServices;
	}

	public void setExpansesServices(IExpansesServices expansesServices) {
		this.expansesServices = expansesServices;
	}

	public static Map<String, Object> getRequest() {
		return request;
	}

	public static void setRequest(Map<String, Object> request) {
		ExpansesAction.request = request;
	}

	public static Map<String, Object> getResponse() {
		return response;
	}

	public static void setResponse(Map<String, Object> response) {
		ExpansesAction.response = response;
	}
	
	

}
