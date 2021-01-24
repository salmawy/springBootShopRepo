package com.gomalmarket.shop.modules.contractor.dao;

import java.util.Date;
import java.util.List;

import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;

public interface IContractorDao {

	List getContractorAccount(int contractorId, int seasonId, int typeId)
			throws DataBaseException, EmptyResultSetException, com.gomalmarket.shop.core.exception.DataBaseException, com.gomalmarket.shop.core.exception.EmptyResultSetException;

	List<String> getSuggestedContractorName(String searchString, int ownerId, int typeId);

	List getContractorAccount(String name, int seasonId, int typeId, Date fromDate, Date toDate, int paid,int ownerId)
			throws DataBaseException, EmptyResultSetException;
}
