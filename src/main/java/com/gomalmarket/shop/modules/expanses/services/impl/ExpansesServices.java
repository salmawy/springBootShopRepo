package com.gomalmarket.shop.modules.expanses.services.impl;

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
import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.Enum.SafeTransactionTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.expanses.IncomeDetail;
import com.gomalmarket.shop.core.entities.expanses.OutcomeDetail;
import com.gomalmarket.shop.core.entities.repos.RepoSupplier;
import com.gomalmarket.shop.core.entities.safe.SafeOfDay;
import com.gomalmarket.shop.core.entities.safe.SafeTracing;
import com.gomalmarket.shop.core.entities.safe.SeasonSafe;
import com.gomalmarket.shop.core.entities.shopLoan.Loaner;
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

 

	public void outcomeTransaction(Date date, double amount, String notes, OutcomeTypeEnum type, int customerId,
			int orderId, Fridage fridage, Season season) throws DataBaseException {
 		OutcomeDetail outcomeDetail = new OutcomeDetail();
		outcomeDetail.setAmount(amount);
		outcomeDetail.setFridageId(fridage.getId());
		outcomeDetail.setSpenderName(shopAppContext.getCurrentUser().getUsername());
		outcomeDetail.setCustomerId(customerId);
		outcomeDetail.setTypeId(type.getId());

		outcomeDetail.setOrderId(orderId);
		outcomeDetail.setSpenderName(shopAppContext.getCurrentUser().getUsername());
		this.getBaseService().addEditBean(outcomeDetail);

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	public void editOutcomeTransaction(Date date, double amount, String notes, OutcomeTypeEnum type, int customerId,
			int orderId, Fridage fridage, Season season, int detailId)
			throws DataBaseException, InvalidReferenceException {

		this.entitDictionary = new HashMap<String, Object>();
		OutcomeDetail detail = (OutcomeDetail) this.getBaseService().findBean(OutcomeDetail.class, detailId);
		deleteOutcomeDetailTransaction(detail);
		//=============================================================================================
		
		outcomeTransaction(date, amount, notes, type, customerId, orderId, fridage, season);
	 
		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

 
	public void editIncomeTransaction(Date date, double amount, String notes, IncomeTypeEnum type, int sellerId,
			int orderId, Fridage fridage, Season season, int detailId)
			throws DataBaseException, InvalidReferenceException {

 		IncomeDetail detail = (IncomeDetail) this.getBaseService().findBean(IncomeDetail.class, detailId);
		deleteIncomeDetailTransaction(detail);
		//=============================================================================================
		
		incomeTransaction(date, amount, notes, type, sellerId, orderId, fridage, season);
	 
		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

 
	 public void incomeTransaction(Date date,double amount, String notes, IncomeTypeEnum type, int sellerId, int orderId, Integer installmentId, Fridage fridage,Season season) throws DataBaseException
	 {

			IncomeDetail incomeDetail = new IncomeDetail();
			incomeDetail.setAmount(amount);
			incomeDetail.setFridageId(fridage.getId());
			incomeDetail.setResipeintName(shopAppContext.getCurrentUser().getUsername());
			incomeDetail.setSellerId(sellerId);
			incomeDetail.setTypeId(type.getId());
			incomeDetail.setInstallmentId(installmentId);
			incomeDetail.setSellerOrderId(orderId);
	this.getBaseService().addEditBean(incomeDetail);
	 //		recalculateSafeBalance(season);

			log.info(this.getClass().getName() + "=>tranasction completed succfully");

		}

	public void incomeTransaction(Date date, double amount, String notes, IncomeTypeEnum type, int sellerId, int orderId,
			Fridage fridage, Season season) throws DataBaseException {

		IncomeDetail incomeDetail = new IncomeDetail();
		incomeDetail.setAmount(amount);
		incomeDetail.setFridageId(fridage.getId());
		incomeDetail.setResipeintName(shopAppContext.getCurrentUser().getUsername());
		incomeDetail.setSellerId(sellerId);
		incomeDetail.setTypeId(type.getId());

		incomeDetail.setSellerOrderId(orderId);
this.getBaseService().addEditBean(incomeDetail);
 //		recalculateSafeBalance(season);

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	
 

	
	@Override
	public void deleteIncomeDetailTransaction(IncomeDetail incomeDetail) throws DataBaseException {
		
		
		 
		
		getBaseService().deleteBean(incomeDetail);
		
 
	//	recalculateSafeBalance(season);

		log.info(this.getClass().getName() + "=>delete tranasction completed succfully");

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

  @Override
	public List getIncomeDates(Season season) throws EmptyResultSetException, DataBaseException {

		return getExpansesDao().getIncomeDates(season.getId());
	}

 

  
	@Override
	public double getSafeBalanceOfday(int seasonId,Date date,SafeTypeEnum type) {
		// TODO Auto-generated method stub
		return getExpansesDao().getSafeBalanceOfday(seasonId, date,type);
	}

	@Override
	public void deleteOutcomeDetailTransaction(OutcomeDetail outcomeDetail) throws DataBaseException {
		getBaseService().deleteBean(outcomeDetail);
		
		 
		//	recalculateSafeBalance(season);

			log.info(this.getClass().getName() + "=>delete tranasction completed succfully");
	
	}

}
