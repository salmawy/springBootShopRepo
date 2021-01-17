package com.gomalmarket.shop.modules.expanses.services.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.Enum.IncomeTypesEnum;
import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.Enum.SafeTransactionTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.Fridage;
import com.gomalmarket.shop.core.entities.IncLoan;
import com.gomalmarket.shop.core.entities.Income;
import com.gomalmarket.shop.core.entities.IncomeDetail;
import com.gomalmarket.shop.core.entities.IncomeType;
import com.gomalmarket.shop.core.entities.LoanAccount;
import com.gomalmarket.shop.core.entities.LoanPaying;
import com.gomalmarket.shop.core.entities.Loaner;
import com.gomalmarket.shop.core.entities.Outcome;
import com.gomalmarket.shop.core.entities.OutcomeDetail;
import com.gomalmarket.shop.core.entities.OutcomeType;
import com.gomalmarket.shop.core.entities.Safe;
import com.gomalmarket.shop.core.entities.SafeOfDay;
import com.gomalmarket.shop.core.entities.SafeTracing;
import com.gomalmarket.shop.core.entities.Season;
import com.gomalmarket.shop.core.entities.repos.RepoSupplier;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;
import com.gomalmarket.shop.modules.expanses.services.dao.IExpansesDao;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Setter
@Getter
@Transactional
public class ExpansesServices implements IExpansesServices {

	@Autowired
	ShopAppContext shopAppContext;
	@Autowired
	IExpansesDao expansesDao;
	@Autowired
	IBaseService baseService;
	
	@Autowired
	RepoSupplier repoSupplier;
///	private ResourceBundle settingsBundle = ResourceBundle.getBundle("ApplicationSettings_ar");
	private Map<String, Object> entitDictionary;

	@Override
	public List getOutcome(Date date) throws EmptyResultSetException, DataBaseException {

		return this.getExpansesDao().getOutcome(date);
	}

	@Override
	public List getIncome(Date date) throws EmptyResultSetException, DataBaseException {

		return this.getExpansesDao().getIncome(date);
	}

	public List getIncomeMonthes(Season season) throws EmptyResultSetException, DataBaseException {
		return this.getExpansesDao().getIncomeMonthes(season.getId());
	}

	public List getOutcomeMonthes(Season season) throws EmptyResultSetException, DataBaseException {
		return this.getExpansesDao().getOutcomeMonthes(season.getId());
	}

	@Override
	public List getOutcomeDays(String month) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub
		return this.getExpansesDao().getOutcomeDays(month);
	}

	@Override
	public List getIncomeDays(String month) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub
		return this.getExpansesDao().getIncomeDays(month);
	}

	public void loanPayTansaction(String name, Date date, double amount, int type, String notes, Fridage fridage)
			throws DataBaseException, InvalidReferenceException {

		Loaner loaner = findLoaner(name);
		LoanAccount account = findLoanerAccount(loaner.getId());

		LoanPaying pay = new LoanPaying();
		pay.setLoanAccount(account);
		pay.setNotes(notes);
		pay.setPaidAmunt(amount);
		pay.setPayingDate(date);
		this.getBaseService().addBean(pay);

		if (type == OutcomeTypeEnum.OUT_PAY_LOAN) {

			OutcomeDetail outPayLoan = new OutcomeDetail();
			outPayLoan.setAmount(amount);
			outPayLoan.setFridage(fridage);
			outPayLoan.setSpenderName(shopAppContext.getCurrentUser().getUsername());

			OutcomeType outcomeType = (OutcomeType) this.getBaseService().findBean(OutcomeType.class,
					OutcomeTypeEnum.OUT_PAY_LOAN);
			outPayLoan.setCustomerId(loaner.getId());

			outPayLoan.setType(outcomeType);
			outPayLoan.setTypeName(String.valueOf(OutcomeTypeEnum.OUT_PAY_LOAN));
			outPayLoan.setOrderId(-1);

			Outcome outome = findOutcome(date);
			saveOutcomeDetail(outPayLoan, outome);

		}

		else if (type == IncomeTypesEnum.IN_PAY_LOAN) {
			IncomeDetail incomeDetail = new IncomeDetail();
			incomeDetail.setAmount(amount);
			incomeDetail.setFridage(fridage);
			incomeDetail.setResipeintName(shopAppContext.getCurrentUser().getUsername());
			incomeDetail.setSellerId(loaner.getId());

			IncomeType incomeType = (IncomeType) this.getBaseService().findBean(IncomeType.class,
					IncomeTypesEnum.IN_PAY_LOAN);

			incomeDetail.setType(incomeType);
			incomeDetail.setTypeName(String.valueOf(IncomeTypesEnum.IN_PAY_LOAN));

			incomeDetail.setSellerOrderId(-1);

			saveIncomeDetail(incomeDetail, date);

		}

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	public void saveIncomeDetail(IncomeDetail incomeDetail, Date date) throws DataBaseException {

		Income income = findIncome(date);
		incomeDetail.setIncome(income);
		incomeDetail.setResipeintName(shopAppContext.getCurrentUser().getUsername());

		income.setTotalAmount(income.getTotalAmount() + incomeDetail.getAmount());
		this.getBaseService().addEditBean(incomeDetail);

		this.getBaseService().addEditBean(income);

	}

	public Income findIncome(Date date) {

		Income income = new Income();
		income.setIncomeDate(date);
		income.setTotalAmount(0.0);

		try {

			income = (Income) this.getExpansesDao().getIncome(date).get(0);

			return income;
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("error.emptyRS incomeDate of date " + date.toString());
		}

		try {
			this.getBaseService().addBean(income);
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return income;

	}

	public Income updateincome(int id, double amount) throws DataBaseException, InvalidReferenceException {

		Income income = (Income) this.getBaseService().findBean(Income.class, id);
		income.setTotalAmount(income.getTotalAmount() + amount);

		return income;

	}

	public void saveOutcomeDetail(OutcomeDetail outcomeDetail, Outcome outcome) throws DataBaseException {

		outcomeDetail.setOutcome(outcome);
		outcomeDetail.setSpenderName(shopAppContext.getCurrentUser().getUsername());

		outcome.setTotalOutcome(outcome.getTotalOutcome() + outcomeDetail.getAmount());
	//	this.getBaseService().addBean(outcomeDetail);

	//	this.getBaseService().editBean(outcome);
		this.repoSupplier.getOutcomeRepo().save(outcome);
		this.repoSupplier.getOutcomeDetailRepo().save(outcomeDetail);

	}

	//@Override
	public Outcome findOutcome(Date date) throws DataBaseException {

		Outcome outcome = null;
		try {

			outcome = (Outcome) this.getExpansesDao().getOutcome(date).get(0);

			try {
				Outcome temp = (Outcome) getSynchronizeBean(Outcome.class, outcome.getId());
				outcome = temp;
				entitDictionary.put(outcome.getClass().getName(), outcome);
			} catch (Exception e) {
				// TODO: handle exception

			}

			return outcome;

		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info( "error.emptyRS incomeDate of date " + date.toString());
		}



		outcome = new Outcome();
		outcome.setOutcomeDate(date);
		outcome.setTotalOutcome(0.0);
		outcome.setSeason(shopAppContext.getSeason());
		this.getBaseService().addBean(outcome);
 		return outcome;
	

 
	}
	public SafeOfDay findSafeOfDay(Date date) throws DataBaseException {

		SafeOfDay  safeOfDay = null;
		try {
			
			 

			safeOfDay = (SafeOfDay) this.getExpansesDao().getSafeOfDay(date).get(0);

			try {
				SafeOfDay temp = (SafeOfDay) getSynchronizeBean(SafeOfDay.class, safeOfDay.getId());
				safeOfDay = temp;
				entitDictionary.put(safeOfDay.getClass().getName(), safeOfDay);
			} catch (Exception e) {
				// TODO: handle exception

			}

			return safeOfDay;

		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info( "error.emptyRS SafeOfDay of date " + date.toString());
		}



		safeOfDay = new SafeOfDay();
		safeOfDay.setDayDate(date);
		safeOfDay.setBalance(0.0);
		safeOfDay.setSeason(shopAppContext.getSeason());
		repoSupplier.getSafeOfDayRepo().save(safeOfDay);
  		return safeOfDay;
	

 
	}

	
	
	
	public SafeOfDay createSafeOfDay(Date date) throws DataBaseException {

		SafeOfDay  safeOfDay = null;
		try {
			
			 

			safeOfDay = (SafeOfDay) this.getExpansesDao().getSafeOfDay(date).get(0);

			try {
				SafeOfDay temp = (SafeOfDay) getSynchronizeBean(SafeOfDay.class, safeOfDay.getId());
				safeOfDay = temp;
				entitDictionary.put(safeOfDay.getClass().getName(), safeOfDay);
			} catch (Exception e) {
				// TODO: handle exception

			}

			return safeOfDay;

		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info( "error.emptyRS SafeOfDay of date " + date.toString());
		}



		safeOfDay = new SafeOfDay();
		safeOfDay.setDayDate(date);
		safeOfDay.setBalance(0.0);
		safeOfDay.setSeason(shopAppContext.getSeason());
		repoSupplier.getSafeOfDayRepo().save(safeOfDay);
  		return safeOfDay;
	

 
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

	public void outcomeTransaction(Date date, double amount, String notes, OutcomeType type, int customerId,
			int orderId, Fridage fridage, Season season) throws DataBaseException {

		OutcomeDetail outcomeDetail = new OutcomeDetail();
		outcomeDetail.setAmount(amount);
		outcomeDetail.setFridage(fridage);
		outcomeDetail.setSpenderName(shopAppContext.getCurrentUser().getUsername());
		outcomeDetail.setCustomerId(customerId);
		outcomeDetail.setType(type);

		outcomeDetail.setOrderId(orderId);
		Outcome outcome = findOutcome(date);
		saveOutcomeDetail(outcomeDetail, outcome);
		recalculateSafeBalance(season);

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}


	public void editOutcomeTransaction(Date date, double amount, String notes, OutcomeType type, int customerId, int orderId,
			Fridage fridage, Season season, int detailId) throws DataBaseException, InvalidReferenceException {

		this.entitDictionary = new HashMap<String, Object>();
		OutcomeDetail detail = (OutcomeDetail) this.getBaseService().findBean(OutcomeDetail.class, detailId);
		this.getBaseService().deleteBean(detail);

		OutcomeDetail outcomeDetail = new OutcomeDetail();
		outcomeDetail.setAmount(amount);
		outcomeDetail.setFridage(fridage);
		outcomeDetail.setSpenderName(shopAppContext.getCurrentUser().getUsername());
		outcomeDetail.setCustomerId(customerId);
		outcomeDetail.setType(type);
		//outcomeDetail.setTypeName(String.valueOf(typeId));

		outcomeDetail.setOrderId(orderId);
		Outcome outcome = findOutcome(date);
		saveOutcomeDetail(outcomeDetail, outcome);
		recalculateSafeBalance(season);

 		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	@Override
	public LoanAccount getLoanerAccount(String name) throws DataBaseException, EmptyResultSetException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		LoanAccount account = null;
	 
		account = (LoanAccount) this.getBaseService().findAllBeansWithDepthMapping(Loaner.class, map).get(0);

		return account;
	}

	@Override
	public LoanAccount getLoanerAccount(int loanerId) throws DataBaseException, EmptyResultSetException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", loanerId);
		LoanAccount account = null;
	 
			account = (LoanAccount) this.getBaseService().findAllBeansWithDepthMapping(Loaner.class, map).get(0);

	 

		return account;
	}

	public void incomeTransaction(Date date, double amount, String notes, IncomeType type, int sellerId, int orderId,
			Fridage fridage, Season season) throws DataBaseException {

		IncomeDetail incomeDetail = new IncomeDetail();
		incomeDetail.setAmount(amount);
		incomeDetail.setFridage(fridage);
		incomeDetail.setResipeintName(shopAppContext.getCurrentUser().getUsername());
		incomeDetail.setSellerId(sellerId);
		incomeDetail.setType(type);

		incomeDetail.setSellerOrderId(orderId);

		saveIncomeDetail(incomeDetail, date);
		recalculateSafeBalance(season);

 	log.info( this.getClass().getName() + "=>tranasction completed succfully");

	}

	public void recalculateSafeBalance(Season season) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("incomeDetail.income.seasonId=", season.getId());

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("outcomeDetail.outcome.seasonId=", season.getId());

		Double totalIncome = 0.0;
		Double totaloutcome = 0.0;

		try {
			totalIncome = (Double) this.getBaseService().aggregate("IncomeDetail incomeDetail", "sum",
					"amount", map);
			totalIncome = (totalIncome == null) ? 0.0 : totalIncome;
			totaloutcome = (Double) this.getBaseService().aggregate("OutcomeDetail outcomeDetail", "sum",
					"amount", map2);
			totaloutcome = (totaloutcome == null) ? 0.0 : totaloutcome;

			map2 = new HashMap<String, Object>();
			map2.put("seasonId", season.getId());

			Safe safe = null;
			try {

				safe = (Safe) this.getBaseService().findAllBeans(Safe.class, map2, null).get(0);
				Safe temp = (Safe) getSynchronizeBean(Safe.class, safe.getId());
				safe = temp;

			} catch (Exception e) {
				// TODO: handle exception
			}
			double temp = totalIncome - totaloutcome;
			safe.setBalance(safe.getBaseAmount() + temp);
			repoSupplier.getSafeRepo().save(safe);
		//	this.getBaseService().addEditBean(safe);

		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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

	public Double getSafeBalance(Season season) {

		Double balance = 0.0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seasonId", season.getId());

		try {
			Safe safe = (Safe) this.getBaseService().findBean(Safe.class, map);
			return safe.getBalance();
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return balance;

	}

	public List getLoanerDebts(int loanerId, String type) throws EmptyResultSetException, DataBaseException {

		return this.getExpansesDao().getLoanerDebts(loanerId, type);
	}

	@Override
	public List getLoanerInstalments(int loanerId, String type) throws EmptyResultSetException, DataBaseException {
		return this.getExpansesDao().getLoanerInstalments(loanerId, type);
	}

	@Override
	public void loanIncTansaction(String name, Date date, double amount, String type, String notes, Fridage fridage,
			Season season) throws DataBaseException, InvalidReferenceException {
		// ======================================================================
		Loaner loaner = new Loaner();
		loaner.setName(name);
		saveLoaner(loaner);
		// ======================================================================
		LoanAccount account = new LoanAccount();
		account.setType(type);
		account.setLoaner(loaner);
		saveLoanerAccount(account);
		// ======================================================================
		IncLoan loan = new IncLoan();
		loan.setAmount(amount);
		loan.setLoanAccount(account);
		loan.setLoanDate(date);
		loan.setNotes(notes);
		this.getBaseService().addBean(loan);
		// ======================================================================

		if (type.equals(LoanTypeEnum.IN_LOAN)) {
			IncomeType incomeType=(IncomeType) this.getBaseService().findBean(IncomeType.class, IncomeTypesEnum.IN_LOAN);

			incomeTransaction(date, amount, notes, incomeType, loaner.getId(), -1, fridage,
					season);

		}

		// ======================================================================

		else if (type.equals(LoanTypeEnum.OUT_LOAN)) {
			
			OutcomeType outcomeType=(OutcomeType) this.getBaseService().findBean(OutcomeType.class, OutcomeTypeEnum.OUT_LOAN);
			outcomeTransaction(date, amount, notes, outcomeType, loaner.getId(), -1, fridage,
					season);
		

		}

		// ======================================================================

		 
		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	 
	public void changeSafeBalance(Safe safe, double amount, int transactionType, String transactionName,
			int transactionId) throws DataBaseException, InvalidReferenceException {
		double newBalance = safe.getBalance();
		if (transactionType == SafeTransactionTypeEnum.add)
			newBalance += amount;
		else if (transactionType == SafeTransactionTypeEnum.subtract)
			newBalance -= amount;

		SafeTracing tracing = new SafeTracing();
		tracing.setSafe(safe);
		tracing.setAmount(amount);
		tracing.setAfterAmount(newBalance);
		tracing.setBeforAmount(safe.getBalance());
		tracing.setTransactionType(transactionType);
		tracing.setTransactionId(transactionId);

		tracing.setTransactionName(transactionName);

		safe.setBalance(newBalance);
		this.getBaseService().addBean(tracing);
		this.getBaseService().addEditBean(safe);
		entitDictionary.put(safe.getClass().getName(), safe);
 
	}

	//@Override
	public void changeOutcomeDetailAmount(OutcomeDetail outcomeDetail, double amount, int transactionTypeId)
			throws DataBaseException, InvalidReferenceException {

		double newAmount = outcomeDetail.getAmount();

		if (transactionTypeId == SafeTransactionTypeEnum.add)
			newAmount += amount;
		else if (transactionTypeId == SafeTransactionTypeEnum.subtract)
			newAmount -= amount;

		outcomeDetail.setAmount(newAmount);
		// =========================================================================================================================

		// change outcome
		Outcome outcome = (Outcome) getSynchronizeBean(Outcome.class, outcomeDetail.getOutcome().getId());
		newAmount = outcome.getTotalOutcome();
		if (transactionTypeId == SafeTransactionTypeEnum.add)
			newAmount += amount;
		else if (transactionTypeId == SafeTransactionTypeEnum.subtract)
			newAmount -= amount;

		outcome.setTotalOutcome(newAmount);
		// =========================================================================================================================

		this.getBaseService().addEditBean(outcomeDetail);
		this.getBaseService().addEditBean(outcome);
		entitDictionary.put(outcome.getClass().getName(), outcome);

		changeSafeBalance(outcome.getSafeId(), amount, SafeTransactionTypeEnum.add, outcomeDetail.getType().getName(),
				outcomeDetail.getId());

	}

	@Override
	public void changeIncomeDetailAmount(IncomeDetail incomeDetail, double amount, int transactionTypeId)
			throws DataBaseException, InvalidReferenceException {

		double newAmount = incomeDetail.getAmount();

		if (transactionTypeId == SafeTransactionTypeEnum.add)
			newAmount += amount;
		else if (transactionTypeId == SafeTransactionTypeEnum.subtract)
			newAmount -= amount;

		incomeDetail.setAmount(newAmount);
		// =========================================================================================================================

		// change outcome
		Income income = incomeDetail.getIncome();
		newAmount = income.getTotalAmount();
		if (transactionTypeId == SafeTransactionTypeEnum.add)
			newAmount += amount;
		else if (transactionTypeId == SafeTransactionTypeEnum.subtract)
			newAmount -= amount;

		income.setTotalAmount(newAmount);
		// =========================================================================================================================

		this.getBaseService().addEditBean(incomeDetail);
		this.getBaseService().addEditBean(income);
		entitDictionary.put(income.getClass().getName(), income);

		changeSafeBalance(income.getSafeId(), amount, SafeTransactionTypeEnum.add, incomeDetail.getType().getName(),
				incomeDetail.getId());

	 

	}

	private Object getSynchronizeBean(Class<?> beanClass, Integer identifier) throws InvalidReferenceException {
		try {

			Object bean = entitDictionary.get(beanClass.getName());
			if (bean != null) {
				Integer id = (Integer) invokeMethode(bean, "getId");
				if (id.equals(identifier))
					return bean;
			}

			bean = this.getBaseService().findBean(beanClass, identifier);
			entitDictionary.put(bean.getClass().getName(), bean);

			return bean;

		} catch (InvalidReferenceException e) {

			throw new InvalidReferenceException(beanClass.getName() + " not found ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object invokeMethode(Object instance, String methodeName) {
		Object returnObj = null;
		try {

			Method methode = instance.getClass().getMethod(methodeName);
			returnObj = methode.invoke(instance);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnObj;

	}

	@Override
	public List getIncomeDates(Season season ) throws EmptyResultSetException, DataBaseException {

		return getExpansesDao().getIncomeDates(season.getId());
	}

	@Override
	public List inExactMatchSearchloanerName(String loanerName, String loanerType)
			throws EmptyResultSetException, DataBaseException {

		return getExpansesDao().inExactMatchSearchloanerName(loanerName, loanerType);

	}

	
	
	
 
 

	 

 
	@Override
public void initEntityDictionary() {
	
	try {
		entitDictionary=new HashMap();
		
	}catch (Exception e) {
		// TODO: handle exception
	}
	
	
}
 
}
