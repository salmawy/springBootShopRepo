package com.gomalmarket.shop.modules.contractor.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gomalmarket.shop.core.Enum.ContractorOwnerEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.action.BaseAction;
import com.gomalmarket.shop.core.action.navigation.Request;
import com.gomalmarket.shop.core.action.navigation.Response;
import com.gomalmarket.shop.modules.contractor.services.IContractorService;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;

public class ContractorAction extends BaseAction {
	private IContractorService contractorService;

	private IExpansesServices expansesServices;
	
    
	protected Request request;
	protected Response response;
	
	public  static  Map<String, Object> request_map;
    public  static  Map<String, Object> response_map;
    public  final  List<ComboBoxItem<Integer>> owners;

    
    
    
    
	public IContractorService getContractorService() {
		return contractorService;
	}
	public void setContractorService(IContractorService contractorService) {
		this.contractorService = contractorService;
	}

    public ContractorAction() {
    	owners=new ArrayList<ComboBoxItem<Integer>>();
    	
    	for(ContractorOwnerEnum owner: ContractorOwnerEnum.values() ) {
    		owners.add(new ComboBoxItem<Integer>(owner.getId(),getMessage(owner.getLabel())));   	}
    	 
    	
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
