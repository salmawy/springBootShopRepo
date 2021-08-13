package com.gomalmarket.shop.modules.expanses.services.dao;

import java.util.Date;
import java.util.List;

import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.expanses.enums.SafeTypeEnum;

public interface IExpansesDao {
	
	 public List getOutcome(Date date) throws EmptyResultSetException, DataBaseException ;
	 public List getIncome(Date date) throws EmptyResultSetException, DataBaseException ;
	 public List getIncomeMonthes(int seasonId) throws EmptyResultSetException, DataBaseException ;
	 public List getOutcomeMonthes(int seasonId) throws EmptyResultSetException, DataBaseException ;
	 public List getOutcomeDays(String month) throws EmptyResultSetException, DataBaseException ;
	 public List getIncomeDays(String month) throws EmptyResultSetException, DataBaseException ;
 	List getIncomeDates(int seasonId) throws EmptyResultSetException, DataBaseException;
	List inExactMatchSearchloanerName(String loanerName, String loanerType)
			throws EmptyResultSetException, DataBaseException;
	List getSafeOfDay(Date date) throws EmptyResultSetException, DataBaseException;
 	public double getSafeBalanceOfday(int seasonId,Date date,SafeTypeEnum type) ;
	 public List getOutcomeDetails(Date outcomeDate) throws EmptyResultSetException, DataBaseException ;
	 public List getIncomeDetails(Date incomeDate) throws EmptyResultSetException, DataBaseException ;
	 
	 public List getOutcomeDetails(int dateId) throws EmptyResultSetException, DataBaseException ;
	 public List getIncomeDetails(int dateId) throws EmptyResultSetException, DataBaseException ;
	List findLoaners(String name) throws EmptyResultSetException, DataBaseException;
	List loadGroupsLoanerNames(LoanTypeEnum loanType) throws EmptyResultSetException, DataBaseException;
}
