package com.gomalmarket.shop.modules.contractor.dao;

import java.util.Date;
import java.util.List;

import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;

public interface IContractorDao {

	public List getNotSettledContractors(int contractorId,int typeId) throws DataBaseException, EmptyResultSetException ;
	List<String> getSuggestedContractorName(String searchString, int ownerId, int typeId);

	List getContractorTransactions(String name, int typeId, Date fromDate, Date toDate, int paid,int ownerId)
			throws DataBaseException, EmptyResultSetException;
}
