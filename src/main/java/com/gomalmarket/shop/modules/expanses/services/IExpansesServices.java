package com.gomalmarket.shop.modules.expanses.services;

import java.util.Date;
import java.util.List;

import com.gomalmarket.shop.core.Enum.IncomeTypeEnum;
import com.gomalmarket.shop.core.Enum.LoanTransactionTypeEnum;
import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.expanses.IncomeDetail;
import com.gomalmarket.shop.core.entities.expanses.OutcomeDetail;
import com.gomalmarket.shop.core.entities.shopLoan.LoanAccount;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.modules.expanses.enums.SafeTypeEnum;
import com.gomalmarket.shop.modules.expanses.view.beans.LoanTransaction;

public interface IExpansesServices {
	 public List getOutcome(Date date) throws EmptyResultSetException, DataBaseException ;
	 public List getIncome(Date date) throws EmptyResultSetException, DataBaseException ;
	 public List getIncomeMonthes(Season season) throws EmptyResultSetException, DataBaseException ;
	 public List getOutcomeMonthes(Season season) throws EmptyResultSetException, DataBaseException ;
	 public List getOutcomeDays(String month) throws EmptyResultSetException, DataBaseException ;
	 public List getIncomeDays(String month) throws EmptyResultSetException, DataBaseException ;
	 public void incomeTransaction(Date date,double amount, String notes, IncomeTypeEnum type, int sellerId, int orderId, Fridage fridage,Season season) throws DataBaseException;
	 public void incomeTransaction(Date date,double amount, String notes, IncomeTypeEnum type, int sellerId, int orderId, Integer installmentId, Fridage fridage,Season season) throws DataBaseException;
	 public List getOutcomeDetails(Date outcomeDate) throws EmptyResultSetException, DataBaseException ;
	 public List getIncomeDetails(Date incomeDate) throws EmptyResultSetException, DataBaseException ;
	
	 public List getOutcomeDetails(int dateId) throws EmptyResultSetException, DataBaseException ;
	 public List getIncomeDetails(int dateId) throws EmptyResultSetException, DataBaseException ;
	
	 
	 public void outcomeTransaction(Date date, double amount, String notes, OutcomeTypeEnum type, int customerId,
				int orderId, Fridage fridage, Season season) throws DataBaseException;
	
	 
 		
 	 public Double getSafeBalance(Season season) ;
     
	 
	
	 
	List getIncomeDates(Season season) throws EmptyResultSetException, DataBaseException;
	public void editIncomeTransaction(Date date, double amount, String notes, IncomeTypeEnum type, int sellerId,
			int orderId, Fridage fridage, Season season, int detailId)
			throws DataBaseException, InvalidReferenceException;
	void editOutcomeTransaction(Date date, double amount, String notes, OutcomeTypeEnum type, int customerId, int orderId,
			Fridage fridage, Season season, int detailId) throws DataBaseException, InvalidReferenceException;
 	void deleteOutcomeDetailTransaction(OutcomeDetail outcomeDetail) throws DataBaseException;
	void deleteIncomeDetailTransaction(IncomeDetail incomeDetail) throws DataBaseException;
	
	public double getSafeBalanceOfday(int seasonId,Date date,SafeTypeEnum type);
	List<LoanTransaction> getLoanTransactions(int loanerId,int groupId, LoanTransactionTypeEnum type)
			throws EmptyResultSetException, DataBaseException;
	List getfindLoaners(String name);
	void editLoanPayTansaction(int LoanTransactionId, double amount, String notes, Date trxDate, LoanTypeEnum loanType,
			Fridage fridage, Season season)
			throws DataBaseException, InvalidReferenceException, EmptyResultSetException;
	void loanTansaction(int loanerId, String loanerName, double amount, String notes, Date trxDate,
			LoanTypeEnum loanType, Fridage fridage, Season season) throws DataBaseException;
	void loanPayTansaction(int loanerId, String loanerName, double amount, String notes, Date trxDate,
			LoanTypeEnum loanType, Fridage fridage, Season season) throws DataBaseException;
	void editLoanTansaction(int LoanTransactionId, double amount, String notes, Date trxDate, LoanTypeEnum loanType,
			Fridage fridage, Season season)
			throws DataBaseException, InvalidReferenceException, EmptyResultSetException;
	LoanAccount getLoanerAccount(int id);
	
	 public List loadGroupsLoanerNames(LoanTypeEnum loanType) throws EmptyResultSetException, DataBaseException ;
}
