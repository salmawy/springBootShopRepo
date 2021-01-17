package com.gomalmarket.shop.modules.Customer.dao;

import java.util.Date;
import java.util.List;

import com.gomalmarket.shop.core.entities.Fridage;
import com.gomalmarket.shop.core.entities.Season;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;

public interface ICustomerDao {
	public List getCustomerOrders(Date orderDate) throws EmptyResultSetException, DataBaseException;
	public List getSeasonCustomers(Season season,Fridage fridage,int typeId) throws EmptyResultSetException, DataBaseException ;
	public Season getCurrentSeason() throws DataBaseException, EmptyResultSetException ;
	public List getCustomersSummaryTransactions(Season season,Fridage fridage,int customerId) throws EmptyResultSetException, DataBaseException ;
	public List getCustomerOrders(int customerId,Season season,Fridage fridage ,int finished) throws EmptyResultSetException, DataBaseException;
	public double  getPurchasedCustomerTotalDue(Season season,int customerId) throws EmptyResultSetException, DataBaseException ;
	public double getPurchasedCustomerTotalInst(Season season,int customerId) throws EmptyResultSetException, DataBaseException ;
	 public List<String> getSuggestedCustomerName(String searchString,int customerTypeId) ;
	 public List getOutcome(Date date) throws EmptyResultSetException, DataBaseException ;
}
