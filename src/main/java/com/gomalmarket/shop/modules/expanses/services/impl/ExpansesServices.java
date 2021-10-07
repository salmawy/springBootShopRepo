package com.gomalmarket.shop.modules.expanses.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.Enum.IncomeTypeEnum;
import com.gomalmarket.shop.core.Enum.LoanTransactionTypeEnum;
import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.expanses.IncomeDetail;
import com.gomalmarket.shop.core.entities.expanses.OutcomeDetail;
import com.gomalmarket.shop.core.entities.repos.LoanAccountRepo;
import com.gomalmarket.shop.core.entities.repos.PayCreditRepo;
import com.gomalmarket.shop.core.entities.repos.PayDebitRepo;
import com.gomalmarket.shop.core.entities.repos.RepoSupplier;
import com.gomalmarket.shop.core.entities.safe.SafeTransaction;
import com.gomalmarket.shop.core.entities.safe.SeasonSafe;
import com.gomalmarket.shop.core.entities.shopLoan.LoanAccount;
import com.gomalmarket.shop.core.entities.shopLoan.LoanCredit;
import com.gomalmarket.shop.core.entities.shopLoan.LoanDebit;
import com.gomalmarket.shop.core.entities.shopLoan.Loaner;
import com.gomalmarket.shop.core.entities.shopLoan.PayCredit;
import com.gomalmarket.shop.core.entities.shopLoan.PayDebit;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.expanses.enums.SafeTypeEnum;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;
import com.gomalmarket.shop.modules.expanses.services.dao.IExpansesDao;
import com.gomalmarket.shop.modules.expanses.view.beans.LoanTransaction;

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

		Date day = new Date(date.getTime());
		day.setHours(0);
		day.setMinutes(0);
		day.setSeconds(0);

		OutcomeDetail outcomeDetail = new OutcomeDetail();
		outcomeDetail.setAmount(amount);
		outcomeDetail.setFridageId(fridage.getId());
		outcomeDetail.setSpenderName(shopAppContext.getCurrentUser().getUsername());
		outcomeDetail.setCustomerId(customerId);
		outcomeDetail.setTypeId(type.getId());
		outcomeDetail.setTransactionDate(date);
		outcomeDetail.setTransactionDay(day);
		outcomeDetail.setSeasonId(season.getId());

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
		// =============================================================================================

		outcomeTransaction(date, amount, notes, type, customerId, orderId, fridage, season);

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	public void editIncomeTransaction(Date date, double amount, String notes, IncomeTypeEnum type, int sellerId,
			int orderId, Fridage fridage, Season season, int detailId)
			throws DataBaseException, InvalidReferenceException {

		IncomeDetail detail = (IncomeDetail) this.getBaseService().findBean(IncomeDetail.class, detailId);
		deleteIncomeDetailTransaction(detail);
		// =============================================================================================

		incomeTransaction(date, amount, notes, type, sellerId, orderId, fridage, season);

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	public void incomeTransaction(Date date, double amount, String notes, IncomeTypeEnum type, int sellerId,
			int orderId, Integer installmentId, Fridage fridage, Season season) throws DataBaseException {

		IncomeDetail incomeDetail = new IncomeDetail();
		incomeDetail.setAmount(amount);
		incomeDetail.setFridageId(fridage.getId());
		incomeDetail.setResipeintName(shopAppContext.getCurrentUser().getUsername());
		incomeDetail.setSellerId(sellerId);
		incomeDetail.setTypeId(type.getId());
		incomeDetail.setInstallmentId(installmentId);
		incomeDetail.setSellerOrderId(orderId);
		incomeDetail.setTransactionDate(date);
		incomeDetail.setTransactionDay(date);
		incomeDetail.setSeasonId(season.getId());

		this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getIncomeDetailRepo(),
				incomeDetail);

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	public void incomeTransaction(Date date, double amount, String notes, IncomeTypeEnum type, int sellerId,
			int orderId, Fridage fridage, Season season) throws DataBaseException {

		IncomeDetail incomeDetail = new IncomeDetail();
		incomeDetail.setAmount(amount);
		incomeDetail.setFridageId(fridage.getId());
		incomeDetail.setResipeintName(shopAppContext.getCurrentUser().getUsername());
		incomeDetail.setSellerId(sellerId);
		incomeDetail.setTypeId(type.getId());
		incomeDetail.setTransactionDate(date);
		incomeDetail.setTransactionDay(date);
		incomeDetail.setSeasonId(season.getId());

		incomeDetail.setSellerOrderId(orderId);
		this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getIncomeDetailRepo(),
				incomeDetail);

//this.getBaseService().addEditBean(incomeDetail);
		// recalculateSafeBalance(season);

		log.info(this.getClass().getName() + "=>tranasction completed succfully");

	}

	@Override
	public void deleteIncomeDetailTransaction(IncomeDetail incomeDetail) throws DataBaseException {

		getBaseService().deleteBean(incomeDetail);

		// recalculateSafeBalance(season);

		log.info(this.getClass().getName() + "=>delete tranasction completed succfully");

	}

	@Override
	public Double getSafeBalance(Season season) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seasonId", season.getId());

		Optional<SeasonSafe> safeOp = repoSupplier.getSafeRepo().findById(season.getId());

		if (safeOp.isPresent()) {

			return safeOp.get().getBalance();
		}

		return 0.0;

	}

	@Override
	public List getIncomeDates(Season season) throws EmptyResultSetException, DataBaseException {

		return getExpansesDao().getIncomeDates(season.getId());
	}

	@Override
	public double getSafeBalanceOfday(int seasonId, Date date, SafeTypeEnum type) {
		// TODO Auto-generated method stub
		double sumOfSafeBalance=getExpansesDao().getSafeBalanceOfday(seasonId, date, type);
		Season season=this.getRepoSupplier().getSeasonRepo().findById(seasonId).get();
		return sumOfSafeBalance+season.getInitaBalance();
		
	}

	@Override
	public void deleteOutcomeDetailTransaction(OutcomeDetail outcomeDetail) throws DataBaseException {
		getBaseService().deleteBean(outcomeDetail);

		// recalculateSafeBalance(season);

		log.info(this.getClass().getName() + "=>delete tranasction completed succfully");

	}

	@Override
	public List getOutcomeDetails(Date outcomeDate) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub
		return getExpansesDao().getOutcomeDetails(outcomeDate);
	}

	@Override
	public List getIncomeDetails(Date incomeDate) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub
		return getExpansesDao().getIncomeDetails(incomeDate);
	}

	@Override
	public List getOutcomeDetails(int dateId) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub
		return getExpansesDao().getOutcomeDetails(dateId);
	}

	@Override
	public List getIncomeDetails(int dateId) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub
		return getExpansesDao().getIncomeDetails(dateId);
	}

	public List<LoanTransaction> getPayCreditTransactions(int loanerId, int groupId,int finished)
			throws DataBaseException, EmptyResultSetException {
		List<LoanTransaction> loanTransactions = null;
		Map<String, Object> paramtersMap = new HashMap<String, Object>();
		paramtersMap.put("loanerId", loanerId);
		paramtersMap.put("finished", finished);
		if (groupId != 0)
			paramtersMap.put("groupId", groupId);

		List<Object> result = this.getBaseService().findAllBeans(PayCredit.class, paramtersMap, null);
		loanTransactions = new ArrayList<LoanTransaction>();

		loanTransactions = (List<LoanTransaction>) result.stream().map(e -> {

			PayCredit t = (PayCredit) e;
			return LoanTransaction.builder().amount(t.getAmount()).id(t.getId()).notes(t.getNotes())
					.transactionDate(LoanTransaction.sdf.format(t.getTransactionDate()))
					.description(LoanTransactionTypeEnum.PAY_CREDIT.getId()).loanerId(t.getLoaner().getId()).build();
		}).collect(Collectors.toList());

		return loanTransactions;
	}

	public List<LoanTransaction> getPayDebetTransactions(int loanerId, int groupId,int finished)
			throws DataBaseException, EmptyResultSetException {
		List<LoanTransaction> loanTransactions = null;
		Map<String, Object> paramtersMap = new HashMap<String, Object>();

		paramtersMap.put("loanerId", loanerId);
		paramtersMap.put("finished", finished);
		if (groupId != 0)
			paramtersMap.put("groupId", groupId);

		List<Object> result = this.getBaseService().findAllBeans(PayDebit.class, paramtersMap, null);
		loanTransactions = new ArrayList<LoanTransaction>();

		loanTransactions = (List<LoanTransaction>) result.stream().map(e -> {

			PayDebit t = (PayDebit) e;
			return LoanTransaction.builder().amount(t.getAmount()).id(t.getId()).notes(t.getNotes())
					.transactionDate(LoanTransaction.sdf.format(t.getTransactionDate()))
					.description(LoanTransactionTypeEnum.PAY_DEBIT.getId()).loanerId(t.getLoaner().getId()).build();
		}).collect(Collectors.toList());

		return loanTransactions;
	}

	public List<LoanTransaction> getLoanCreditTransactions(int loanerId, int groupId,int finished)
			throws DataBaseException, EmptyResultSetException {

		List<LoanTransaction> loanTransactions = null;
		Map<String, Object> paramtersMap = new HashMap<String, Object>();
		paramtersMap.put("loanerId", loanerId);
		paramtersMap.put("finished", finished);
		if (groupId != 0)
			paramtersMap.put("groupId", groupId);

		List<Object> result = this.getBaseService().findAllBeans(LoanCredit.class, paramtersMap, null);

		loanTransactions = (List<LoanTransaction>) result.stream().map(e -> {

			LoanCredit t = (LoanCredit) e;
			return LoanTransaction.builder().amount(t.getAmount()).id(t.getId()).notes(t.getNotes())
					.transactionDate(LoanTransaction.sdf.format(t.getTransactionDate()))
					.description(LoanTransactionTypeEnum.LOAN_CREDIT.getId()).loanerId(t.getLoaner().getId()).build();
		}).collect(Collectors.toList());

		return loanTransactions;
	}

	public List<LoanTransaction> getLoanDebetTransactions(int loanerId, int groupId,int finished)
			throws DataBaseException, EmptyResultSetException {
		List<LoanTransaction> loanTransactions = null;
		Map<String, Object> paramtersMap = new HashMap<String, Object>();
		paramtersMap.put("loanerId", loanerId);
		paramtersMap.put("finished", finished);
		if (groupId != 0)
			paramtersMap.put("groupId", groupId);
		List<Object> result = this.getBaseService().findAllBeans(LoanDebit.class, paramtersMap, null);

		loanTransactions = (List<LoanTransaction>) result.stream().map(e -> {

			LoanDebit t = (LoanDebit) e;
			return LoanTransaction.builder().amount(t.getAmount()).id(t.getId()).notes(t.getNotes())
					.transactionDate(LoanTransaction.sdf.format(t.getTransactionDate()))
					.description(LoanTransactionTypeEnum.LOAN_DEBET.getId()).loanerId(t.getLoaner().getId()).build();
		}).collect(Collectors.toList());

		return loanTransactions;
	}

	@Override
	public List<LoanTransaction> getLoanTransactions(int loanerId, int groupId, LoanTransactionTypeEnum type,int finished)
			throws EmptyResultSetException, DataBaseException {

		switch (type) {
		case PAY_CREDIT:
			return getPayCreditTransactions(loanerId, groupId,finished);

		case PAY_DEBIT:

			return getPayDebetTransactions(loanerId, groupId,finished);
		case LOAN_CREDIT:

			return getLoanCreditTransactions(loanerId, groupId,finished);
		case LOAN_DEBET:

			return getLoanDebetTransactions(loanerId, groupId,finished);

		default:
			return null;
		}

	}

	@Override
	public List<String> getfindLoaners(String name) {
		List<String>  loaners = new ArrayList<>();

		try {
			loaners = this.getExpansesDao().findLoaners(name);
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			log.info("name not found ");
		}
		
		 catch ( DataBaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return loaners;

	}

	@Override
	@Transactional
	public void loanTansaction(int loanerId, String loanerName, double amount, String notes, Date trxDate,
			LoanTypeEnum loanType, Fridage fridage, Season season) throws DataBaseException {

		Loaner loaner = this.findLoaner(loanerName);

		if (loaner == null) {
			loaner = new Loaner();
			loaner.setName(loanerName);
			this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getLoanerRepo(), loaner);

		}
		loanerId = loaner.getId();

		switch (loanType) {
		case OUT_LOAN:

			LoanCredit loanCredit = new LoanCredit();
			loanCredit.setAmount(amount);
			loanCredit.setLoanerId(loanerId);
			loanCredit.setNotes(notes);
			loanCredit.setTransactionDate(trxDate);
			loanCredit.setFinished(0);
			this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getLoanCreditRepo(),
					loanCredit);

			this.outcomeTransaction(trxDate, amount, notes, OutcomeTypeEnum.OUT_LOAN, loanerId, loanCredit.getId(),
					fridage, season);

			break;
		case IN_LOAN:
			LoanDebit loanDebit = new LoanDebit();
			loanDebit.setAmount(amount);
			loanDebit.setLoanerId(loanerId);
			loanDebit.setNotes(notes);
			loanDebit.setTransactionDate(trxDate);
			loanDebit.setFinished(0);
			this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getLoanCreditRepo(), loanDebit);
			this.incomeTransaction(trxDate, amount, notes, IncomeTypeEnum.IN_LOAN, loanerId, loanDebit.getId(), fridage,
					season);

			break;
		default:
			break;
		}

	}

	@Override
	@Transactional
	public void loanPayTansaction(int loanerId, String loanerName, double amount, String notes, Date trxDate,
			LoanTypeEnum loanType, Fridage fridage, Season season) throws DataBaseException {

		switch (loanType) {
		case OUT_LOAN:

			PayCredit payCredit = new PayCredit();
			payCredit.setAmount(amount);
			payCredit.setLoanerId(loanerId);
			payCredit.setNotes(notes);
			payCredit.setTransactionDate(trxDate);
			payCredit.setFinished(0);

			this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getPayCreditRepo(), payCredit);
			this.incomeTransaction(trxDate, amount, notes, IncomeTypeEnum.PAY_CREDIT, loanerId, payCredit.getId(),
					fridage, season);

			break;
		case IN_LOAN:
			PayDebit payDebit = new PayDebit();
			payDebit.setAmount(amount);
			payDebit.setLoanerId(loanerId);
			payDebit.setNotes(notes);
			payDebit.setTransactionDate(trxDate);
			payDebit.setFinished(0);
			this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getPayDebitRepo(), payDebit);
			this.outcomeTransaction(trxDate, amount, notes, OutcomeTypeEnum.PAY_DEBIT, loanerId, payDebit.getId(),
					fridage, season);

			break;
		default:
			break;
		}

	}

	@Override
	public void editLoanTansaction(int LoanTransactionId, double amount, String notes, Date trxDate,
			LoanTypeEnum loanType, Fridage fridage, Season season)
			throws DataBaseException, InvalidReferenceException, EmptyResultSetException {

		Map<String, Object> prams = new HashMap<String, Object>();
		prams.put("orderId", LoanTransactionId);
		SafeTransaction safeTransaction = (SafeTransaction) this.getBaseService().findBean(SafeTransaction.class,
				prams);

		switch (loanType) {
		case OUT_LOAN:

			LoanCredit loanCredit = this.getShopAppContext().getRepoSupplier().getLoanCreditRepo()
					.findById(LoanTransactionId).get();
			loanCredit.setAmount(amount);
			loanCredit.setNotes(notes);
			loanCredit.setTransactionDate(trxDate);
			loanCredit.setFinished(0);
			this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getLoanCreditRepo(),
					loanCredit);

			this.editOutcomeTransaction(trxDate, amount, notes, OutcomeTypeEnum.OUT_LOAN, loanCredit.getLoanerId(),
					loanCredit.getId(), fridage, season, safeTransaction.getId());

			break;
		case IN_LOAN:
			LoanDebit loanDebit = this.getShopAppContext().getRepoSupplier().getLoanDebitRepo()
					.findById(LoanTransactionId).get();
			loanDebit.setAmount(amount);
			loanDebit.setNotes(notes);
			loanDebit.setTransactionDate(trxDate);
			loanDebit.setFinished(0);
			this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getLoanCreditRepo(), loanDebit);
			this.editIncomeTransaction(trxDate, amount, notes, IncomeTypeEnum.IN_LOAN, loanDebit.getLoanerId(),
					loanDebit.getId(), fridage, season, safeTransaction.getId());

			break;
		default:
			break;
		}

	}

	@Override
	public void editLoanPayTansaction(int LoanTransactionId, double amount, String notes, Date trxDate,
			LoanTypeEnum loanType, Fridage fridage, Season season)
			throws DataBaseException, InvalidReferenceException, EmptyResultSetException {

		Map<String, Object> prams = new HashMap<String, Object>();
		prams.put("orderId", LoanTransactionId);
		SafeTransaction safeTransaction = (SafeTransaction) this.getBaseService().findBean(SafeTransaction.class,
				prams);
		switch (loanType) {
		case OUT_LOAN:
			PayCreditRepo repo = this.getShopAppContext().getRepoSupplier().getPayCreditRepo();

			PayCredit payCredit = repo.findById(LoanTransactionId).get();
			payCredit.setAmount(amount);
			payCredit.setNotes(notes);
			payCredit.setTransactionDate(trxDate);
			payCredit.setFinished(0);
			this.getBaseService().saveEntity(repo, payCredit);
			this.editIncomeTransaction(trxDate, amount, notes, IncomeTypeEnum.PAY_CREDIT, payCredit.getLoanerId(),
					payCredit.getId(), fridage, season, safeTransaction.getId());

			break;
		case IN_LOAN:
			PayDebitRepo payDebitRepo = this.getShopAppContext().getRepoSupplier().getPayDebitRepo();

			PayDebit payDebit = payDebitRepo.findById(LoanTransactionId).get();
			payDebit.setAmount(amount);
			payDebit.setNotes(notes);
			payDebit.setTransactionDate(trxDate);
			payDebit.setFinished(0);
			this.getBaseService().saveEntity(payDebitRepo, payDebit);
			editOutcomeTransaction(trxDate, amount, notes, OutcomeTypeEnum.PAY_DEBIT, payDebit.getLoanerId(),
					payDebit.getId(), fridage, season, safeTransaction.getId());
			break;
		default:
			break;
		}

	}

	@Override
	public LoanAccount getLoanerAccount(int id) {
		LoanAccountRepo loanAccountRepo = repoSupplier.getLoanAccountRepo();
		Optional<LoanAccount> account = loanAccountRepo.findById(id);

		return (account.isPresent()) ? account.get() : null;

	}

	@Override
	public List loadGroupsLoanerNames(LoanTypeEnum loanType) throws EmptyResultSetException, DataBaseException {
		return getExpansesDao().loadGroupsLoanerNames(loanType);

	}

	@Override
	public Date GetLoanStartDate(int loanerId, LoanTypeEnum loanType) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanerId=", loanerId);
		// map.put("season.id=", getAppContext().getSeason().getId());
		map.put("groupId is", " null");

		Date date = null;

		String tableName = (loanType.equals(LoanTypeEnum.IN_LOAN)) ? "LoanDebit" : "LoanCredit";

		try {
			date = (Date) this.getBaseService().aggregate(tableName, "min", "transactionDate", map);
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;

	}

	
	private int getMaxGroupId() {
		
		int maxId = 0;

		String tableName ="ShopLoanTransaction";

		try {
			maxId = (int) this.getBaseService().aggregate(tableName, "max", "groupId", null);
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return maxId;

	
		
		
	}
	
	@Transactional
	private void groupOutLoan(int loanerId) throws DataBaseException {

		Map<String, Object> paramtersMap = new HashMap<String, Object>();
		paramtersMap.put("loanerId", loanerId);
		paramtersMap.put("groupId", "is null");
		paramtersMap.put("finished", 0);
		int maxGroupId=getMaxGroupId();
		List<LoanCredit> loans=null;
		try {
			loans = this.getBaseService().gFindAllBeansWithDepthMapping(LoanCredit.class, paramtersMap);//(LoanCredit.class, paramtersMap, null);
			loans.stream().forEach(e-> {
				e.setFinished(1);
				e.setGroupId(maxGroupId);
			});
			
			Iterable<LoanCredit> loansIterable = loans;
			
			getRepoSupplier().getLoanCreditRepo().saveAll(loansIterable);

		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//-------------------------------------------------------------------------------------------------------------------
		List<PayCredit> payees=null;
		try {
			payees = this.getBaseService().gFindAllBeansWithDepthMapping(PayCredit.class, paramtersMap);
			payees.stream().forEach(e-> {
				e.setFinished(1);
				e.setGroupId(maxGroupId);
			});
			
			Iterable<PayCredit> payeesIterable = payees;
			
			getRepoSupplier().getPayCreditRepo().saveAll(payeesIterable);

		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		


	}

	@Transactional
	private void groupInLoan(int loanerId) throws DataBaseException {

		Map<String, Object> paramtersMap = new HashMap<String, Object>();
		paramtersMap.put("loanerId", loanerId);
		paramtersMap.put("groupId", "is null");
		paramtersMap.put("finished", 0);
		int groupId=getMaxGroupId()+1;
		
//-------------------------------------------------------------------------------------------
		List<LoanDebit> loans=null;
		try {
			loans = this.getBaseService().gFindAllBeansWithDepthMapping(LoanDebit.class, paramtersMap);
			loans.stream().forEach(e-> {
				e.setFinished(1);
				e.setGroupId(groupId);
			});
			
			Iterable<LoanDebit> loansIterable = loans;
			
			getRepoSupplier().getLoanDebitRepo().saveAll(loansIterable);

		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//-------------------------------------------------------------------------------------------------------------------
		List<PayDebit> payees=null;
		try {
			payees = this.getBaseService().gFindAllBeansWithDepthMapping(PayDebit.class, paramtersMap);
			payees.stream().forEach(e-> {
				e.setFinished(1);
				e.setGroupId(groupId);
			});
			
			Iterable<PayDebit> payeesIterable = payees;
			
			getRepoSupplier().getPayDebitRepo().saveAll(payeesIterable);

		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		


	}

	
	
	
	@Override
	public void groupLoan(int loanerId, LoanTypeEnum loanType) throws DataBaseException {
		
		
		switch (loanType) {
		case IN_LOAN:
			groupInLoan(loanerId);
			break;
		case OUT_LOAN:
			groupOutLoan(loanerId);
			break;
		default:
			break;
		}
		
		
		
		
		
		
		
		
		
		
	}
	
	
	//---------------------------------------------------------------------------------------------------------------
	@Override
	public  LoanAccount getLoanAccount(int loanerId,String name) {
		LoanAccount account=null;
		Map<String,Object> params=new HashMap<String, Object>();
		
		if(name!=null&&!name.isEmpty())
		params.put("name", name);
		
		if(loanerId!=0)
			params.put("id", loanerId);
		
		try {
			account= this.getBaseService().gFindBean(LoanAccount.class, params);
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			log.info("loan account not found");
		}
		
		return account;
		
	}
		
	@Override
public void deleteLoanTransaction(int trxId,LoanTransactionTypeEnum trxType) throws DataBaseException {

	switch (trxType) {
	case PAY_CREDIT:
		 deletePayCreditTransactions(trxId);
break;
	case PAY_DEBIT:

		 deletePayDebitTransactions(trxId);
		 break;
	case LOAN_CREDIT:

		 deleteLoanCreditTransactions(trxId);
		 break;
	case LOAN_DEBET:

		 deleteLoanDebitTransactions(trxId);
		 break;

	default:
		 break;
	}

}

@Transactional
private void deletePayCreditTransactions(int trxId) throws DataBaseException {
	
	getRepoSupplier().getPayCreditRepo().deleteById(trxId);
	
	
 	
	Map<String, Object> prams = new HashMap<String, Object>();
	prams.put("orderId", trxId);
	IncomeDetail incomeDetail;
	try {
		incomeDetail = (IncomeDetail) this.getBaseService().findBean(IncomeDetail.class,prams);
		deleteIncomeDetailTransaction(incomeDetail);

	} catch (EmptyResultSetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// TODO Auto-generated method stub
 }


@Transactional
private void deletePayDebitTransactions(int trxId) throws DataBaseException {
	
	getRepoSupplier().getPayDebitRepo().deleteById(trxId);
	
	
 	
	Map<String, Object> prams = new HashMap<String, Object>();
	prams.put("orderId", trxId);
	OutcomeDetail outcomeDetail;
	try {
		outcomeDetail = (OutcomeDetail) this.getBaseService().findBean(OutcomeDetail.class,prams);
		deleteOutcomeDetailTransaction(outcomeDetail);

	} catch (EmptyResultSetException e) {
		// TODO Auto-generated catch block
		log.info("deletePayDebitTransactions not  found trx ");
	}
	// TODO Auto-generated method stub
 }



@Transactional
private void deleteLoanDebitTransactions(int trxId) throws DataBaseException {
	
	getRepoSupplier().getLoanDebitRepo().deleteById(trxId);
	
	
 	
 
	Map<String, Object> prams = new HashMap<String, Object>();
	prams.put("orderId", trxId);
	IncomeDetail incomeDetail;
	try {
		incomeDetail = (IncomeDetail) this.getBaseService().findBean(IncomeDetail.class,prams);
		deleteIncomeDetailTransaction(incomeDetail);

	} catch (EmptyResultSetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// TODO Auto-generated method stub
 }


@Transactional
private void deleteLoanCreditTransactions(int trxId) throws DataBaseException {
	
	getRepoSupplier().getLoanCreditRepo().deleteById(trxId);
	
	
 	
	Map<String, Object> prams = new HashMap<String, Object>();
	prams.put("orderId", trxId);
	OutcomeDetail outcomeDetail;
	try {
		outcomeDetail = (OutcomeDetail) this.getBaseService().findBean(OutcomeDetail.class,prams);
		deleteOutcomeDetailTransaction(outcomeDetail);

	} catch (EmptyResultSetException e) {
		// TODO Auto-generated catch block
		log.info("deletePayDebitTransactions not  found trx ");
	}
	// TODO Auto-generated method stub
 }



 

}