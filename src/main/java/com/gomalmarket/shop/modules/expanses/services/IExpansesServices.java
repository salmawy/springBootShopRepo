package com.gomalmarket.shop.modules.expanses.services;

import java.util.Date;
import java.util.List;

import com.gomalmarket.shop.core.entities.Fridage;
import com.gomalmarket.shop.core.entities.IncomeDetail;
import com.gomalmarket.shop.core.entities.IncomeType;
import com.gomalmarket.shop.core.entities.LoanAccount;
import com.gomalmarket.shop.core.entities.Outcome;
import com.gomalmarket.shop.core.entities.OutcomeDetail;
import com.gomalmarket.shop.core.entities.OutcomeType;
import com.gomalmarket.shop.core.entities.SafeOfDay;
import com.gomalmarket.shop.core.entities.Season;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.modules.expanses.enums.SafeTypeEnum;

public interface IExpansesServices {
	 public List getOutcome(Date date) throws EmptyResultSetException, DataBaseException ;
	 public List getIncome(Date date) throws EmptyResultSetException, DataBaseException ;
	 public List getIncomeMonthes(Season season) throws EmptyResultSetException, DataBaseException ;
	 public List getOutcomeMonthes(Season season) throws EmptyResultSetException, DataBaseException ;
	 public List getOutcomeDays(String month) throws EmptyResultSetException, DataBaseException ;
	 public List getIncomeDays(String month) throws EmptyResultSetException, DataBaseException ;
	 public void incomeTransaction(Date date,double amount, String notes, IncomeType type, int sellerId, int orderId, Fridage fridage,Season season) throws DataBaseException;

	 public void outcomeTransaction(Date date, double amount, String notes, OutcomeType type, int customerId,
				int orderId, Fridage fridage, Season season) throws DataBaseException;
	
	 
	 public void loanPayTansaction(String name,Date date,double amount,int type,String notes,Fridage fridage)throws DataBaseException, InvalidReferenceException ;
		
	 public List getLoanerDebts(int loanerId, String type) throws EmptyResultSetException, DataBaseException ;	
	 public Double getSafeBalance(Season season) ;
	 public LoanAccount getLoanerAccount(String name) throws DataBaseException, EmptyResultSetException;
	 public List getLoanerInstalments(int loanerId, String type) throws EmptyResultSetException, DataBaseException ;
	 public LoanAccount getLoanerAccount(int loanerId) throws DataBaseException, EmptyResultSetException;
	void loanIncTansaction(String name, Date date, double amount, String type, String notes, Fridage fridage,Season season)	throws DataBaseException, InvalidReferenceException;
	void changeSafeBalance(SafeOfDay safe, double amount, int transactionType, String transactionName,int transactionId)
			throws DataBaseException, InvalidReferenceException;
	 
	void changeOutcomeDetailAmount(OutcomeDetail outcomeDetail, double amount, int transactionTypeId)
			throws DataBaseException, InvalidReferenceException;
	
	
	void changeIncomeDetailAmount( IncomeDetail incomeDetail, double amount, int transactionTypeId)
			throws DataBaseException, InvalidReferenceException;
 
	List getIncomeDates(Season season) throws EmptyResultSetException, DataBaseException;
	List inExactMatchSearchloanerName(String loanerName, String loanerType)
			throws EmptyResultSetException, DataBaseException;
	void editOutcomeTransaction(Date date, double amount, String notes, OutcomeType type, int customerId, int orderId,
			Fridage fridage, Season season, int detailId) throws DataBaseException, InvalidReferenceException;
	Outcome findOrCreateOutcome(Date date) throws DataBaseException;
	void initEntityDictionary();
	SafeOfDay findOrCreateSafeOfDay(Date date) throws DataBaseException;
	void deleteOutcomeDetailTransaction(OutcomeDetail outcomeDetail) throws DataBaseException;
	void deleteIncomeDetailTransaction(IncomeDetail incomeDetail) throws DataBaseException;
	
	public double getSafeBalanceOfday(int seasonId,Date date,SafeTypeEnum type);
}
