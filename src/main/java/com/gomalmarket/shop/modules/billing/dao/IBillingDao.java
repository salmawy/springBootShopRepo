package com.gomalmarket.shop.modules.billing.dao;

import java.util.List;

import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;

public interface IBillingDao {

	List getSuggestedCustomersOrders(int status, int seasonId, int fridageId, int typeId) throws EmptyResultSetException, DataBaseException;

	List getCustomersOrderWeights(int orderId) throws EmptyResultSetException, DataBaseException;

	List getSuggestedCustomersOrders(int seasonId, int fridageId) throws EmptyResultSetException, DataBaseException;

}
