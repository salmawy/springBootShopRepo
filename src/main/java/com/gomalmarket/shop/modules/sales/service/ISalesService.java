package com.gomalmarket.shop.modules.sales.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gomalmarket.shop.core.entities.Fridage;
import com.gomalmarket.shop.core.entities.Seller;
import com.gomalmarket.shop.core.entities.SellerLoanBag;
import com.gomalmarket.shop.core.entities.SellerOrder;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;

public interface ISalesService {
	
	public List getAllCustomersOrdersTags(int seasonId,int fridageId,int productId,int storeId) ;
	public Object  aggregate(String tablename,String operation,String columnName,Map <String,Object>parameters) throws DataBaseException, EmptyResultSetException ;
	public List<String> getSuggestedSellerName(String searchString) ;
	public List getSellersOrders(Date orderDate) throws EmptyResultSetException, DataBaseException ;
	public void saveSellerOrder(Seller seller, SellerOrder sellerOrder,double paidAmount) throws Exception ;
	//public void editSellerOrder(Seller seller, SellerOrder sellerOrder,double paidAmount,SellerOrder oldOrder) throws Exception ;
	 public List getSellersDebts( int seasonId,int active) throws EmptyResultSetException, DataBaseException;
	 public double getSeasonStartTotalSellersLoan(int seasonId) ;
	 public double getSeasoncCurrentotalSellersLoan(int seasonId) ;
	SellerLoanBag getSellerLoanBag(int sellerId, int seasonId) throws DataBaseException;
	void saveSellerInstalment(int sellerId, int sellerOrderId, int sellerLoanBagId, Fridage fridage, double amount,
			Date date, String notes) throws DataBaseException, InvalidReferenceException;
	void editeSellerOrder(Seller newSeller, SellerOrder newOrder, double paidAmount, SellerOrder oldOrder,
			int seasonId) throws Exception;
	void initEntityDictionary();
	List getSellersLoanSummary(Date fromDate, Date toDate, int seasonId)
			throws EmptyResultSetException, DataBaseException;
 	List getSellerOrderWeights(int orderId) throws DataBaseException, EmptyResultSetException;
	
}
