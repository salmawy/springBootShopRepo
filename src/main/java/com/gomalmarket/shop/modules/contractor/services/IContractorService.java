package com.gomalmarket.shop.modules.contractor.services;

import java.util.Date;
import java.util.List;

import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;

public interface IContractorService {

	List getContractorAccount(int contractorId, int seasonId, int typeId)
			throws DataBaseException, EmptyResultSetException;

	void contractorTransaction(String name, int typeId, double amount, int fridageId, String notes, int paid,
			int ownerId, Date date, int seasonId) throws DataBaseException;

	List<String> getSuggestedContractorName(String searchString, int ownerId, int typeId);
	List getContractorAccount(String name, int seasonId, int typeId, Date fromDate, Date toDate, int paid,int ownerId)
			throws DataBaseException, EmptyResultSetException;
}
