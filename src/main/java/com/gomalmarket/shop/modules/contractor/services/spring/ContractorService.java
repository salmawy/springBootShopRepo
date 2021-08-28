package com.gomalmarket.shop.modules.contractor.services.spring;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.contractor.Contractor;
import com.gomalmarket.shop.core.entities.contractor.ContractorAccount;
import com.gomalmarket.shop.core.entities.contractor.ContractorTransaction;
import com.gomalmarket.shop.core.entities.expanses.OutcomeDetail;
import com.gomalmarket.shop.core.entities.repos.RepoSupplier;
import com.gomalmarket.shop.core.entities.safe.SafeTransaction;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.contractor.dao.IContractorDao;
import com.gomalmarket.shop.modules.contractor.services.IContractorService;
import com.gomalmarket.shop.modules.contractor.view.beans.ContractorVB;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@Setter
@Getter
public class ContractorService implements IContractorService {

	@Autowired
	IExpansesServices expansesService;
	@Autowired
	IBaseService baseService;
	@Autowired
	IContractorDao contractorDao;

	@Autowired
	RepoSupplier repoSupplier;
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	ShopAppContext appContext;

	@Override
	public List getAllContractorsAccounts(int typeId, int ownerId, int seasonId)
			throws DataBaseException, EmptyResultSetException {
		return this.getContractorDao().getAllContractorsAccounts(typeId, ownerId, seasonId);
	}

	@Override
	public ContractorTransaction AddContractorTransaction(String name, int typeId, double amount, Fridage fridage,
			String notes, int paid, int ownerId, Date date, Season season)
			throws DataBaseException, InvalidReferenceException {

		// ===================save contractor into Database
		// ========================================================
		Contractor contractor = saveContractor(name, typeId, ownerId);

		// ========================save contractor detail into Database
		// ========================================================
		ContractorTransaction trx = new ContractorTransaction();
		trx.setAmount(amount);
		trx.setContractor(contractor);
		trx.setTransactionDate(date);
		trx.setPaid(paid);
		trx.setReport(notes);
		trx.setSpenderName(appContext.getCurrentUser().getUsername());
		trx.setSeason(season);

		this.baseService.saveEntity(repoSupplier.getContractorTransactionRepo(), trx);

		if (trx.getPaid() == 1) {

			this.getExpansesService().outcomeTransaction(date, amount, notes, OutcomeTypeEnum.K_L, contractor.getId(),
					trx.getId(), fridage, season);
		}
		return trx;
	}

	
	@Override
	public ContractorVB getcontractorAccount(int contractorId,int seasonId) {
		
		Map params=new HashMap();
		params.put("seasonId", seasonId);
		params.put("contracor.id", contractorId);
		ContractorVB contractorVB=null;
		try {
			List<ContractorAccount> accounts=	this.getBaseService().gFindAllBeansWithDepthMapping(ContractorAccount.class, params);
			ContractorAccount account=accounts.get(0);
			
			contractorVB =new ContractorVB();
			contractorVB.setAccountId(account.getId());
			contractorVB.setAmount(account.getAmount());
			contractorVB.setContractorId(account.getContractor().getId());
			contractorVB.setName(account.getContractorName());
			
			return contractorVB;
			
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ContractorTransaction editContractorTransaction(String name, int typeId, double amount, Fridage fridage,
			String notes, int paid, int ownerId, Date date, Season season, int trxId)
			throws DataBaseException, InvalidReferenceException {

		// ========================save contractor detail into Database
		// ========================================================
		ContractorTransaction trx = repoSupplier.getContractorTransactionRepo().findById(trxId).get();
		trx.setAmount(amount);
		trx.setTransactionDate(date);
		trx.setPaid(paid);
		trx.setReport(notes);
		trx.setSpenderName(appContext.getCurrentUser().getUsername());
		trx.setSeason(season);

		this.baseService.saveEntity(repoSupplier.getContractorTransactionRepo(), trx);

		if (trx.getPaid() == 1) {

			Map<String, Object> prams = new HashMap<String, Object>();
			prams.put("orderId", trxId);
			SafeTransaction safeTransaction;
			try {
				safeTransaction = (SafeTransaction) this.getBaseService().findBean(SafeTransaction.class, prams);
				this.getExpansesService().editOutcomeTransaction(date, amount, notes, OutcomeTypeEnum.K_L,
						trx.getContractor().getId(), trx.getId(), fridage, season, safeTransaction.getId());

			} catch (EmptyResultSetException e) {
				log.info("safe transaction not found of contracor transaction id : " + trxId);
			}

		}
		return trx;
	}

	public Contractor saveContractor(String name, int typeId, int ownerId) throws DataBaseException {

		Contractor contractor = new Contractor();
		contractor.setName(name);
		contractor.setTypeId(typeId);
		contractor.setOwnerId(ownerId);

		try {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("name", name);
			m.put("typeId", typeId);
			m.put("ownerId", ownerId);
			contractor = (Contractor) this.getBaseService().findAllBeans(Contractor.class, m, null).get(0);
			return contractor;
		} catch (DataBaseException | EmptyResultSetException e) {
			this.getBaseService().addBean(contractor);

		}

		return contractor;

	}

	@Override
	public List<String> getSuggestedContractorName(String searchString, int ownerId, int typeId) {
		return this.contractorDao.getSuggestedContractorName(searchString, ownerId, typeId);
	}

	@Override
	public List getContractorTransactions(String name, int typeId, Date fromDate, Date toDate, int paid, int ownerId)
			throws DataBaseException, EmptyResultSetException {
		// TODO Auto-generated method stub
		return this.getContractorDao().getContractorTransactions(name, typeId, fromDate, toDate, paid, ownerId);
	}

	
	@Override
	public void deleteContractorTransaction(int trxId) {

		ContractorTransaction trx = repoSupplier.getContractorTransactionRepo().findById(trxId).get();
		repoSupplier.getContractorTransactionRepo().findById(trxId).get();

		//-------------------------------------------------------------------------------------------------------------------------------------
		if (trx.getPaid() == 1) {

			Map<String, Object> prams = new HashMap<String, Object>();
			prams.put("orderId", trxId);
			try {
				OutcomeDetail outcomeDetail = this.getBaseService().gFindBean(OutcomeDetail.class, prams);

				getExpansesService().deleteOutcomeDetailTransaction(outcomeDetail);

			} catch (EmptyResultSetException | DataBaseException e) {
				log.info("safe transaction not found of contracor transaction id : " + trxId);
			}

		}
		//-------------------------------------------------------------------------------------------------------------------------------------
	
		repoSupplier.getContractorTransactionRepo().delete(trx);
	
	}
}
