package com.gomalmarket.shop.modules.expanses.services.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.Enum.IncomeTypeEnum;
import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.Enum.SafeTransactionTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.Fridage;
import com.gomalmarket.shop.core.entities.ShopLoan;
import com.gomalmarket.shop.core.entities.Income;
import com.gomalmarket.shop.core.entities.IncomeDetail;
import com.gomalmarket.shop.core.entities.IncomeType;
import com.gomalmarket.shop.core.entities.LoanAccount;
import com.gomalmarket.shop.core.entities.LoanPaying;
import com.gomalmarket.shop.core.entities.Loaner;
import com.gomalmarket.shop.core.entities.Outcome;
import com.gomalmarket.shop.core.entities.OutcomeDetail;
import com.gomalmarket.shop.core.entities.OutcomeType;
import com.gomalmarket.shop.core.entities.SeasonSafe;
import com.gomalmarket.shop.core.entities.SafeOfDay;
import com.gomalmarket.shop.core.entities.SafeTracing;
import com.gomalmarket.shop.core.entities.Season;
import com.gomalmarket.shop.core.entities.repos.RepoSupplier;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.expanses.enums.SafeTypeEnum;
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
	 @PersistenceContext
	    EntityManager entityManger;

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

 public void saveIncomeDetail(IncomeDetail incomeDetail, Date date) throws DataBaseException {

		Income income = findOrCreateIncome(date);
		incomeDetail.setIncome(income);
		incomeDetail.setResipeintName(shopAppContext.getCurrentUser().getUsername());

		income.setTotalAmount(income.getTotalAmount() + incomeDetail.getAmount());
		
		
		changeSafeBalance(incomeDetail.getIncome().getSafe(), incomeDetail.getAmount(), SafeTransactionTypeEnum.add, incomeDetail.getType().getName(), incomeDetail.getId());

		this.getBaseService().addEditBean(incomeDetail);

		this.getBaseService().addEditBean(income);

	}

	public Income findOrCreateIncome(Date date) throws DataBaseException {

		Income income = null;

		try {

			income = (Income) this.getExpansesDao().getIncome(date).get(0);
			if(income.getSafe()==null) {
				income.setSafe(findOrCreateSafeOfDay(income.getIncomeDate()));
				
			}

			return income;
		} catch ( EmptyResultSetException e) {
			// TODO Auto-generated catch block
 			log.info("error.emptyRS incomeDate of date " + date.toString());
		}
	
		
		
		 income = new Income();
		income.setIncomeDate(date);
		income.setTotalAmount(0.0);
		income.setSafe(findOrCreateSafeOfDay(income.getIncomeDate()));
		this.getBaseService().addBean(income);
	 

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

		outcome.setTotalAmount(outcome.getTotalAmount() + outcomeDetail.getAmount());
		
		changeSafeBalance(outcomeDetail.getOutcome().getSafe(), outcomeDetail.getAmount(), SafeTransactionTypeEnum.subtract, outcomeDetail.getType().getName(), outcomeDetail.getId());

		  this.getBaseService().addBean(outcomeDetail);

	 this.getBaseService().editBean(outcome);
		 

	}

	 @Override
	public Outcome findOrCreateOutcome(Date date) throws DataBaseException {

		Outcome outcome = null;
		try {

			outcome = (Outcome) this.getExpansesDao().getOutcome(date).get(0);
			if(outcome.getSafe()==null) {
				outcome.setSafe(findOrCreateSafeOfDay(outcome.getOutcomeDate()));
			}
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
			log.info("error.emptyRS incomeDate of date " + date.toString());
		}

		outcome = new Outcome();
		outcome.setOutcomeDate(date);
		outcome.setTotalAmount(0.0);
		outcome.setSeason(shopAppContext.getSeason());
		outcome.setSafe(findOrCreateSafeOfDay(outcome.getOutcomeDate()));
		this.getBaseService().addBean(outcome);
		return outcome;

	}

	@Override
	public SafeOfDay findOrCreateSafeOfDay(Date date) throws DataBaseException {

		SafeOfDay safeOfDay = null;
		//###############################################################################################################

		try {

			safeOfDay = (SafeOfDay) this.getExpansesDao().getSafeOfDay(date).get(0);
			/*
			 * if (safeOfDay.getParent() == null) {
			 * safeOfDay.setParent(getParent(safeOfDay));
			 * 
			 * }
			 */
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
			log.info("error.emptyRS SafeOfDay of date " + date.toString());
		}
//###############################################################################################################
 		safeOfDay =  new SafeOfDay();
		safeOfDay.setDayDate(date);
		safeOfDay.setBalance(0.0);
		//safeOfDay.setParent(getParent(safeOfDay));
		safeOfDay.setSeason(shopAppContext.getSeason());
		repoSupplier.getSafeOfDayRepo().save(safeOfDay);
		return safeOfDay;

	}

	private SafeOfDay getParent(SafeOfDay safeOfDay) throws DataBaseException {

		Date dayDate = safeOfDay.getDayDate();
		try {
			return getExpansesDao().getParentSafeOfDay(dayDate);
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public SafeOfDay createSafeOfDay(Date date) throws DataBaseException {

		SafeOfDay safeOfDay = null;
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
			log.info("error.emptyRS SafeOfDay of date " + date.toString());
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

	public void outcomeTransaction(Date date, double amount, String notes, OutcomeTypeEnum type, int customerId,
			int orderId, Fridage fridage, Season season) throws DataBaseException {
this.initEntityDictionary();
		OutcomeDetail outcomeDetail = new OutcomeDetail();
		outcomeDetail.setAmount(amount);
		outcomeDetail.setFridage(fridage);
		outcomeDetail.setSpenderName(shopAppContext.getCurrentUser().getUsername());
		outcomeDetail.setCustomerId(customerId);
		outcomeDetail.setTypeId(type.getId());

		outcomeDetail.setOrderId(orderId);
		Outcome outcome = findOrCreateOutcome(date);
		saveOutcomeDetail(outcomeDetail, outcome);
 

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	public void editOutcomeTransaction(Date date, double amount, String notes, OutcomeTypeEnum type, int customerId,
			int orderId, Fridage fridage, Season season, int detailId)
			throws DataBaseException, InvalidReferenceException {

		this.entitDictionary = new HashMap<String, Object>();
		OutcomeDetail detail = (OutcomeDetail) this.getBaseService().findBean(OutcomeDetail.class, detailId);
		deleteOutcomeDetailTransaction(detail);

		
		

		OutcomeDetail outcomeDetail = new OutcomeDetail();
		outcomeDetail.setAmount(amount);
		outcomeDetail.setFridage(fridage);
		outcomeDetail.setSpenderName(shopAppContext.getCurrentUser().getUsername());
		outcomeDetail.setCustomerId(customerId);
		outcomeDetail.setTypeId(type.getId());
		// outcomeDetail.setTypeName(String.valueOf(typeId));

		outcomeDetail.setOrderId(orderId);
		Outcome outcome = findOrCreateOutcome(date);
		saveOutcomeDetail(outcomeDetail, outcome);
	 

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

 

 
	

	public void incomeTransaction(Date date, double amount, String notes, IncomeTypeEnum type, int sellerId, int orderId,
			Fridage fridage, Season season) throws DataBaseException {

		IncomeDetail incomeDetail = new IncomeDetail();
		incomeDetail.setAmount(amount);
		incomeDetail.setFridage(fridage);
		incomeDetail.setResipeintName(shopAppContext.getCurrentUser().getUsername());
		incomeDetail.setSellerId(sellerId);
		incomeDetail.setTypeId(type.getId());

		incomeDetail.setSellerOrderId(orderId);

		saveIncomeDetail(incomeDetail, date);
//		recalculateSafeBalance(season);

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	
	@Override
	public void deleteOutcomeDetailTransaction(OutcomeDetail outcomeDetail) throws DataBaseException {
		
		
		
		SafeOfDay safeOfDay=outcomeDetail.getOutcome().getSafe();
		Outcome outcome=outcomeDetail.getOutcome();
	
		outcome.setTotalAmount(outcome.getTotalAmount()-outcomeDetail.getAmount());
		getBaseService().addEditBean(outcome);
		
		
		changeSafeBalance(safeOfDay, outcomeDetail.getAmount(), SafeTransactionTypeEnum.add, outcomeDetail.getType().getName(), outcomeDetail.getId());
		 
		
		
		
		
		getBaseService().deleteBean(outcomeDetail);
		
 
	//	recalculateSafeBalance(season);

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	
	@Override
	public void deleteIncomeDetailTransaction(IncomeDetail incomeDetail) throws DataBaseException {
		
		
		
		SafeOfDay safeOfDay=incomeDetail.getIncome().getSafe();
		Income income=incomeDetail.getIncome();
	
		income.setTotalAmount(income.getTotalAmount()-incomeDetail.getAmount());
		getBaseService().addEditBean(income);
		changeSafeBalance(safeOfDay, incomeDetail.getAmount(), SafeTransactionTypeEnum.subtract, incomeDetail.getType().getName(), incomeDetail.getId());
		 
		
		
		
		
		getBaseService().deleteBean(incomeDetail);
		
 
	//	recalculateSafeBalance(season);

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	


	

	@Override
	public Double getSafeBalance(Season season) { 
		
		Map <String,Object>params=new HashMap<String,Object>();
		params.put("seasonId", season.getId());
		
	Optional<SeasonSafe>	safeOp= repoSupplier.getSafeRepo().findById(season.getId());
				
		if(safeOp.isPresent()) {
			
			return safeOp.get().getBalance();
		}
		
		return 0.0;
		
	}

 
	
	
	
	
	
	
	
	public void changeSafeBalance(SafeOfDay safeOfDay, double amount, int transactionType, String transactionName,
			int transactionId) throws DataBaseException {
		
		
		double newBalance = safeOfDay.getBalance();
		if (transactionType == SafeTransactionTypeEnum.add)
			newBalance += amount;
		else if (transactionType == SafeTransactionTypeEnum.subtract)
			newBalance -= amount;

		SafeTracing tracing = new SafeTracing();
		tracing.setSafeOfDay(safeOfDay);
		tracing.setAmount(amount);
		tracing.setAfterAmount(newBalance);
		tracing.setBeforAmount(safeOfDay.getBalance());
		tracing.setTransactionType(transactionType);
		tracing.setTransactionId(transactionId);

		tracing.setTransactionName(transactionName);

		safeOfDay.setBalance(newBalance);
		this.getBaseService().addBean(tracing);
		this.getBaseService().addEditBean(safeOfDay);
		entitDictionary.put(safeOfDay.getClass().getName(), safeOfDay);

	}

	// @Override
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
		newAmount = outcome.getTotalAmount();
		if (transactionTypeId == SafeTransactionTypeEnum.add)
			newAmount += amount;
		else if (transactionTypeId == SafeTransactionTypeEnum.subtract)
			newAmount -= amount;

		outcome.setTotalAmount(newAmount);
		// =========================================================================================================================

		this.getBaseService().addEditBean(outcomeDetail);
		this.getBaseService().addEditBean(outcome);
		entitDictionary.put(outcome.getClass().getName(), outcome);

		changeSafeBalance(outcome.getSafe(), amount, SafeTransactionTypeEnum.add, outcomeDetail.getType().getName(),
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

		changeSafeBalance(income.getSafe(), amount, SafeTransactionTypeEnum.add, incomeDetail.getType().getName(),
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
	public List getIncomeDates(Season season) throws EmptyResultSetException, DataBaseException {

		return getExpansesDao().getIncomeDates(season.getId());
	}

 

	@Override
	public void initEntityDictionary() {

		try {
			entitDictionary = new HashMap();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public double getSafeBalanceOfday(int seasonId,Date date,SafeTypeEnum type) {
		// TODO Auto-generated method stub
		return getExpansesDao().getSafeBalanceOfday(seasonId, date,type);
	}

}
