package com.gomalmarket.shop.modules.Customer.service;

import java.util.Date;
import java.util.List;

import com.gomalmarket.shop.core.entities.Customer;
import com.gomalmarket.shop.core.entities.CustomerOrder;
import com.gomalmarket.shop.core.entities.Fridage;
import com.gomalmarket.shop.core.entities.Season;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;

public interface ICustomerService {
	
	public List getCustomerInvoices(Season season,int customerId,Fridage fridage) throws EmptyResultSetException, DataBaseException ;
	public List getCustomerOrders(Season season,int customerId,Fridage fridage) throws EmptyResultSetException, DataBaseException ;

	public List getCustomerOrders(Date orderDate) throws EmptyResultSetException, DataBaseException ;
	public List getSeasonCustomers(Season season,Fridage fridage,int typeId) throws EmptyResultSetException, DataBaseException ;
	public Season getCurrentSeason() throws DataBaseException, EmptyResultSetException ;
	public List getCustomersSummaryTransactions(Season season,Fridage fridage,int customerId) throws EmptyResultSetException, DataBaseException ;
	public List  getPurchasedCustomerData(Season season,Fridage fridage) throws EmptyResultSetException, DataBaseException ;
	 public List<String> getSuggestedCustomerName(String searchString,int customerTypeId) ;

	 public void saveCustomerOrder(CustomerOrder customerOrder) throws DataBaseException, InvalidReferenceException ;
	 public void editCustomerOrder(CustomerOrder newValue,CustomerOrder oldValue) throws DataBaseException, InvalidReferenceException ;
	void payPurchasedOrder(Customer customer,double amount,Date date,String notes,Season season,Fridage fridage)
			throws DataBaseException, InvalidReferenceException;
	void editPurchasedOrder(int installmentId, Customer customer ,double amount,Date date,String notes,Season season,Fridage fridage)
			throws DataBaseException, InvalidReferenceException;

}
