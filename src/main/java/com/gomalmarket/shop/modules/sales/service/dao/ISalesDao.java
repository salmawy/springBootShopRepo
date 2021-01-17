package com.gomalmarket.shop.modules.sales.service.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;

public interface ISalesDao {
	
	public Object  aggregate(String tablename,String operation,String columnName,Map <String,Object>parameters) throws DataBaseException, EmptyResultSetException, com.gomalmarket.shop.core.exception.DataBaseException, com.gomalmarket.shop.core.exception.EmptyResultSetException ;
	 public List<String> getSuggestedSellerName(String searchString) ;
		public List getSellersOrders(Date orderDate) throws EmptyResultSetException, DataBaseException ;
		 public List getSellersDebts( int seasonId,int active) throws EmptyResultSetException, DataBaseException;
		 public List getIncome(Date date) throws EmptyResultSetException, DataBaseException ;
		List getSellersLoanSummary(Date fromDate, Date toDate, int seasonId)
				throws EmptyResultSetException, DataBaseException;
}
