package com.gomalmarket.shop.modules.Customer.action;

import java.util.Map;

 
import com.gomalmarket.shop.core.action.BaseAction;
import com.gomalmarket.shop.modules.Customer.service.ICustomerService;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CustomerBaseAction extends BaseAction {

	public static Map<String, Object> request;
	public static Map<String, Object> response;

	private ICustomerService customerService;

	private IExpansesServices expansesService;

	public CustomerBaseAction() {
		customerService = (ICustomerService) getSpringBeanFactory().getBean("customerService");
		expansesService = (IExpansesServices) getSpringBeanFactory().getBean("expansesServices");

	}

}
