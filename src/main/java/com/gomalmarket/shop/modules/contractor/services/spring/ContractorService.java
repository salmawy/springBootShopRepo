package com.gomalmarket.shop.modules.contractor.services.spring;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.contractor.Contractor;
import com.gomalmarket.shop.core.entities.contractor.ContractorTransaction;
import com.gomalmarket.shop.core.entities.repos.RepoSupplier;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.contractor.dao.IContractorDao;
import com.gomalmarket.shop.modules.contractor.services.IContractorService;
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
	public List getNotSettledContractors(int contractorId, int typeId)
			throws DataBaseException, EmptyResultSetException {
		return this.getContractorDao().getNotSettledContractors(contractorId, typeId);
	}

	@Override

	public void contractorTransaction(String name, int typeId, double amount, int fridageId, String notes, int paid,
			int ownerId, Date date, Season season) throws DataBaseException, InvalidReferenceException {

		// ===================save contractor into Database
		// ========================================================
		Contractor contractor = saveContractor(name, typeId, ownerId);

		// ========================save contractor detail into Database
		// ========================================================
		ContractorTransaction accountDetail = new ContractorTransaction();
		accountDetail.setAmount(amount);
		accountDetail.setContractor(contractor);
		accountDetail.setTransactionDate(date);
		accountDetail.setPaid(paid);
		accountDetail.setReport(notes);
		accountDetail.setSpenderName(appContext.getCurrentUser().getUsername());
		accountDetail.setSeason(season);
		this.getBaseService().addBean(accountDetail);
		// this.getBaseService().addEditBean(contractorAccount);
		// =============================== insert outcomeTransaction total
		// value=====================================

		if (accountDetail.getPaid() == 1) {

			this.getExpansesService().outcomeTransaction(date, amount, notes, OutcomeTypeEnum.K_L, contractor.getId(),
					-1, getAppContext().getFridage(), season);
		}

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
	public List getContractorTransactions(String name, int typeId, Date fromDate, Date toDate, int paid,
			int ownerId) throws DataBaseException, EmptyResultSetException {
		// TODO Auto-generated method stub
		return this.getContractorDao().getContractorTransactions(name, typeId, fromDate, toDate, paid, ownerId);
	}

}
