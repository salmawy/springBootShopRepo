package com.gomalmarket.shop.modules.shopLoans.services.spring;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.Enum.CustomerTypeEnum;
import com.gomalmarket.shop.core.Enum.IncomeTypeEnum;
import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.CustomerOrder;
import com.gomalmarket.shop.core.entities.Fridage;
import com.gomalmarket.shop.core.entities.ShopLoan;
import com.gomalmarket.shop.core.entities.IncomeDetail;
import com.gomalmarket.shop.core.entities.IncomeType;
import com.gomalmarket.shop.core.entities.LoanAccount;
import com.gomalmarket.shop.core.entities.LoanPaying;
import com.gomalmarket.shop.core.entities.Loaner;
import com.gomalmarket.shop.core.entities.Outcome;
import com.gomalmarket.shop.core.entities.OutcomeDetail;
import com.gomalmarket.shop.core.entities.OutcomeType;
import com.gomalmarket.shop.core.entities.Season;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.Customer.service.ICustomerService;
import com.gomalmarket.shop.modules.billing.dao.IBillingDao;
import com.gomalmarket.shop.modules.billing.services.IBillingService;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;
import com.gomalmarket.shop.modules.shopLoans.dao.IShoploanDao;
import com.gomalmarket.shop.modules.shopLoans.services.IShopLoanService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;




@Slf4j
@Repository
@Transactional
@Service
@Getter
public class ShopLoanService implements IShopLoanService {
 
	
	@Autowired
	private IExpansesServices expansesServices;
	
	@Autowired
	private ShopAppContext shopAppContext;
	@Autowired
    private IBaseService baseService;
	
	@Autowired
	    private IShoploanDao shoploanDao;

	@Override
	public List<LoanAccount> getLoaners(LoanTypeEnum type) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub
		return shoploanDao.getLoaners(type);
	}

	@Override
	public List getLoans(int accountId) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub
		return shoploanDao.getLoans(accountId);
	}

	@Override
	public List getLoanerInst(int accountId) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub
		return shoploanDao. getLoanerInst(accountId);
	}

	@Override
	public double loanBalance(LoanTypeEnum type) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub
		return getShoploanDao().loanBalance(type);
	}
        
	@Override
	public List inExactMatchSearchloanerName(String loanerName, LoanTypeEnum loanerType)
			throws EmptyResultSetException, DataBaseException {

		return getShoploanDao().inExactMatchSearchloanerName(loanerName, loanerType);

	}
	
	
	
	@Transactional
	public void loanPayTansaction(String name, Date date, double amount, LoanTypeEnum loanTypeEnum , String notes, Fridage fridage,Season season)
			throws DataBaseException, InvalidReferenceException {

		
 		Loaner loaner = findLoaner(name);
		LoanAccount account = findLoanerAccount(loaner.getId());

		LoanPaying pay =  new LoanPaying();
		pay.setLoanAccount(account);
		pay.setNotes(notes);
		pay.setPaidAmunt(amount);
		pay.setPayingDate(date);
		this.getBaseService().addBean(pay);

		
		switch (loanTypeEnum) {
		case IN_LOAN:
			expansesServices.outcomeTransaction(date, amount, notes, OutcomeTypeEnum.OUT_PAY_LOAN, loaner.getId(), -1, fridage, season);

			break;
		case OUT_LOAN:
			expansesServices.incomeTransaction(date, amount, notes, IncomeTypeEnum.IN_PAY_LOAN, loaner.getId(), -1, fridage, season);
			break;
		default:
			break;
		}
	 
 
		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	public LoanAccount findLoanerAccount(int loanerId) throws DataBaseException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanerId", loanerId);

		LoanAccount account = null;
		try {
			account = (LoanAccount) this.getBaseService().findBean(Loaner.class, map);
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return account;

	}
	
	
	
	public Loaner findLoaner(String name) throws DataBaseException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);

		Loaner loaner = null;
		try {
			loaner = (Loaner) this.getBaseService().findBean(Loaner.class, map);
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return loaner;

	}
	
	
	//@Override
	public void loanIncTansaction(Loaner loaner, Date date, double amount, String type, String notes, Fridage fridage,
			Season season) throws DataBaseException, InvalidReferenceException {
		
		saveLoaner(loaner);
		// ======================================================================
		LoanAccount account = new LoanAccount();
		account.setType(type);
		account.setLoaner(loaner);
		saveLoanerAccount(account);
		// ======================================================================
		ShopLoan loan = new ShopLoan();
		loan.setAmount(amount);
		loan.setLoanAccount(account);
		loan.setLoanDate(date);
		loan.setNotes(notes);
		this.getBaseService().addBean(loan);
		// ======================================================================

		if (type.equals(LoanTypeEnum.IN_LOAN)) {
		

			expansesServices. incomeTransaction(date, amount, notes, IncomeTypeEnum.IN_LOAN, loaner.getId(), -1, fridage, season);

		}

		// ======================================================================

		else if (type.equals(LoanTypeEnum.OUT_LOAN)) {

  			this.expansesServices.outcomeTransaction(date, amount, notes, OutcomeTypeEnum.OUT_LOAN, loaner.getId(), -1, fridage, season);

		}

		// ======================================================================

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	
	/*
	 * public List getLoanerDebts(int loanerId, String type) throws
	 * EmptyResultSetException, DataBaseException {
	 * 
	 * return this.getExpansesDao().getLoanerDebts(loanerId, type); }
	 * 
	 * @Override public List getLoanerInstalments(int loanerId, String type) throws
	 * EmptyResultSetException, DataBaseException { return
	 * this.getExpansesDao().getLoanerInstalments(loanerId, type); }
	 */

	
	 public Loaner saveLoaner(Loaner Loaner) throws DataBaseException {

		try {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("name", Loaner.getName());
			Loaner = (Loaner) this.getBaseService().findAllBeans(Loaner.class, m, null).get(0);

			return Loaner;
		} catch (DataBaseException | EmptyResultSetException e) {
			this.getBaseService().addBean(Loaner);

		}

		return Loaner;

	}
	 public LoanAccount saveLoanerAccount(LoanAccount account) throws DataBaseException {

			try {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("loanerId", account.getLoaner().getId());
				m.put("type", account.getType());
				m.put("finished", 0);

				account = (LoanAccount) this.getBaseService().findAllBeans(LoanAccount.class, m, null).get(0);

				return account;
			} catch (DataBaseException | EmptyResultSetException e) {

				this.getBaseService().addBean(account);

			}

			return account;

		}
}