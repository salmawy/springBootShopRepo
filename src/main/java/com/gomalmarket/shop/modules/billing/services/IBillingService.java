package com.gomalmarket.shop.modules.billing.services;

import java.util.List;

import com.gomalmarket.shop.core.entities.customers.CustomerOrder;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;

public interface IBillingService {

	List getSuggestedOrders(int customerId,int status, int seasonId, int typeId ,int fridageId)
			throws DataBaseException, EmptyResultSetException;

	List getSuggestedCustomersOrders(int status, int seasonId, int fridageId, int typeId) throws EmptyResultSetException, DataBaseException;

	List getCustomersOrderWeights(int orderId) throws EmptyResultSetException, DataBaseException;
	List getSuggestedCustomersOrders(int seasonId, int fridageId) throws EmptyResultSetException, DataBaseException;

	void generateInvoice(CustomerOrder invoice) throws DataBaseException, InvalidReferenceException;

 
	public void payInvoice(CustomerOrder invoice) throws DataBaseException, InvalidReferenceException ;

}
