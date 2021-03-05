package com.gomalmarket.shop.modules.billing.services.spring;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.Enum.CustomerTypeEnum;
import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.CustomerOrder;
import com.gomalmarket.shop.core.entities.OutcomeType;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.Customer.service.ICustomerService;
import com.gomalmarket.shop.modules.billing.dao.IBillingDao;
import com.gomalmarket.shop.modules.billing.services.IBillingService;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;




@Slf4j
@Repository
@Transactional
@Service
@Getter
public class BillingService implements IBillingService {
 
	
	
	
	
	@Autowired
	    private ICustomerService customerService;
	
	@Autowired
	    private IExpansesServices expansesService;
	
	@Autowired
	    private IBaseService baseService;
	
	@Autowired
 	    private IBillingDao billingDao;
	    
	@Autowired
	    private ShopAppContext shopAppContext;
    
        
 
 
@Override
	public List getSuggestedOrders(int customerId,int status, int seasonId,int typeId,int fridageId) throws DataBaseException, EmptyResultSetException{
		
		Map<String,Object> map=new HashMap<String, Object>();
		if(typeId!=0)
		map.put("customer.typeId",typeId );
		if(typeId!=0)
			map.put("customer.id",customerId );
		map.put("seasonId", seasonId);
		map.put("invoiceStatus",status );

		map.put("fridageId",fridageId );
 		
	return	this.getBaseService().findAllBeansWithDepthMapping(CustomerOrder.class, map);
		
	
		
	}


@Override
public List getSuggestedCustomersOrders(int status, int seasonId, int fridageId, int typeId) throws EmptyResultSetException, DataBaseException {
	
	
	return getBillingDao().getSuggestedCustomersOrders(status, seasonId, fridageId, typeId);
	
}



@Override
public List getCustomersOrderWeights(int orderId) throws EmptyResultSetException, DataBaseException {

	return this.getBillingDao().getCustomersOrderWeights(orderId);
	}
@Override
public List getSuggestedCustomersOrders(int seasonId, int fridageId) throws EmptyResultSetException, DataBaseException {
 	return getBillingDao().getSuggestedCustomersOrders(seasonId, fridageId);
}

@Override
public void generateInvoice(CustomerOrder invoice ) throws DataBaseException, InvalidReferenceException {

	 
	
	
	
	
	 this.getBaseService().editBean(invoice);  
	 
 	 this.getExpansesService().outcomeTransaction(new Date(), invoice.getTips(), invoice.getNotes(),OutcomeTypeEnum.INVOICE_TIPS, invoice.getCustomer().getId(), invoice.getId(), shopAppContext.getFridage(), shopAppContext.getSeason());		 
	 
 
		 
	

}
@Override
public void payInvoice(CustomerOrder invoice) throws DataBaseException, InvalidReferenceException { 
		
		
		
		
 
    
    if(invoice.getCustomer().getType().getId()==CustomerTypeEnum.purchases||
    		
    		invoice.getCustomer().getType().getId()==CustomerTypeEnum.kareem||
    		invoice.getCustomer().getType().getId()==CustomerTypeEnum.mahmed)
     {
 
    	this.getExpansesService().outcomeTransaction(invoice.getDueDate(), invoice.getNetPrice(), "",OutcomeTypeEnum.ORDER_PAY  , invoice.getCustomer().getId(), invoice.getId(), shopAppContext.getFridage(), shopAppContext.getSeason());
     }

 this.getBaseService().addEditBean(invoice);
	
 
 
	 

}

}
