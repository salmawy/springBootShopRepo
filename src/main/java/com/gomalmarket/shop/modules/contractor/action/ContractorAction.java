package com.gomalmarket.shop.modules.contractor.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.action.BaseAction;
import com.gomalmarket.shop.modules.contractor.services.IContractorService;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;

public class ContractorAction extends BaseAction {
	private IContractorService contractorService;

	private IExpansesServices expansesServices;
	
    public  static  Map<String, Object> request;
    public  static  Map<String, Object> response;
    public  final  List owners;

    
    
    
    
	public IContractorService getContractorService() {
		return contractorService;
	}
	public void setContractorService(IContractorService contractorService) {
		this.contractorService = contractorService;
	}

    public ContractorAction() {
    	owners=new ArrayList (Arrays.asList(new ComboBoxItem(1,getMessage("label.owner.name.kareem")), new ComboBoxItem(2,getMessage("label.owner.name.mahmed"))));
    	setContractorService( (IContractorService) getSpringBeanFactory().getBean("contractorService")); 
		setExpansesServices((IExpansesServices) getSpringBeanFactory().getBean("expansesServices")); 

	}
	public IExpansesServices getExpansesServices() {
		return expansesServices;
	}
	public void setExpansesServices(IExpansesServices expansesServices) {
		this.expansesServices = expansesServices;
	}
    
}
