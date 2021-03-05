package com.gomalmarket.shop.modules.shopLoans.services;

import java.util.List;

import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.entities.LoanAccount;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;

public interface IShopLoanService {
	 public List<LoanAccount> getLoaners(LoanTypeEnum type) throws EmptyResultSetException, DataBaseException;
	 
	   public List getLoans(int accountId) throws EmptyResultSetException, DataBaseException;
	    public List getLoanerInst(int accountId)throws EmptyResultSetException, DataBaseException ;
	    public double loanBalance(LoanTypeEnum type)throws EmptyResultSetException, DataBaseException;

		List inExactMatchSearchloanerName(String loanerName, LoanTypeEnum loanerType)
				throws EmptyResultSetException, DataBaseException;

}
