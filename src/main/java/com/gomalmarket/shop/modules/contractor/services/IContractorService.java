package com.gomalmarket.shop.modules.contractor.services;

import java.util.Date;
import java.util.List;

import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;

public interface IContractorService {

	List getContractorAccount(int contractorId, int seasonId, int typeId)
			throws DataBaseException, EmptyResultSetException;

	public void contractorTransaction(String name,int typeId,double amount,int fridageId,String notes,int paid,int ownerId,Date date,Season season) throws DataBaseException, InvalidReferenceException ;

	List<String> getSuggestedContractorName(String searchString, int ownerId, int typeId);
	List getContractorAccount(String name, int seasonId, int typeId, Date fromDate, Date toDate, int paid,int ownerId)
			throws DataBaseException, EmptyResultSetException;
}
