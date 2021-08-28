package com.gomalmarket.shop.modules.contractor.services;

import java.util.Date;
import java.util.List;

import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.contractor.ContractorTransaction;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.modules.contractor.view.beans.ContractorVB;

public interface IContractorService {

	public List getAllContractorsAccounts(int typeId, int ownerId, int seasonId)
			throws DataBaseException, EmptyResultSetException;

	public ContractorTransaction AddContractorTransaction(String name, int typeId, double amount, Fridage fridageId,
			String notes, int paid, int ownerId, Date date, Season season)
			throws DataBaseException, InvalidReferenceException;

	List<String> getSuggestedContractorName(String searchString, int ownerId, int typeId);

	List getContractorTransactions(String name, int typeId, Date fromDate, Date toDate, int paid, int ownerId)
			throws DataBaseException, EmptyResultSetException;

	public ContractorTransaction editContractorTransaction(String name, int typeId, double amount, Fridage fridage,
			String notes, int paid, int ownerId, Date date, Season season, int trxId)
			throws DataBaseException, InvalidReferenceException;

	void deleteContractorTransaction(int trxId);

	ContractorVB getcontractorAccount(int contractorId, int seasonId);
}
