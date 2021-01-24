package com.gomalmarket.shop.modules.contractor.services.spring;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.entities.Contractor;
import com.gomalmarket.shop.core.entities.ContractorAccount;
import com.gomalmarket.shop.core.entities.ContractorAccountDetail;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
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

 	IExpansesServices expansesService ;
	IBaseService baseService;
	IContractorDao contractorDao;
  
	@Override
	public List getContractorAccount(int contractorId, int seasonId, int typeId)
			throws DataBaseException, EmptyResultSetException {
		return this.getContractorDao().getContractorAccount(contractorId, seasonId, typeId);
	}

	@Override
	
	public void contractorTransaction(String name,int typeId,double amount,int fridageId,String notes,int paid,int ownerId,Date date,int seasonId) throws DataBaseException {

		//===================save contractor into Database ========================================================	 
		 Contractor contractor=saveContractor(name, typeId, ownerId);
		 ContractorAccount contractorAccount=findContractorAccount(contractor.getId());
		 
	//========================save contractor detail into Database ========================================================	
		 ContractorAccountDetail accountDetail=new ContractorAccountDetail();
		 accountDetail.setAmount(amount);
		 accountDetail.setContractorAccountId(contractorAccount.getId());
		 accountDetail.setDetailDate(date);
		 accountDetail.setPaid(paid);
		 accountDetail.setReport(notes);
		 accountDetail.setSpenderName(ApplicationContext.currentUser.getUsername());
		 accountDetail.setSeasonId(seasonId);
		this.getBaseService().addBean(accountDetail); 
		contractorAccount.setTotalAmount(contractorAccount.getTotalAmount()+amount);
		this.getBaseService().addEditBean(contractorAccount); 

	//=============================== insert outcomeTransaction  total value=====================================
	this.getExpansesService().outcomeTransaction(date, amount, notes, OutcomeTypeEnum.K_L, contractor.getId(), -1, fridageId, seasonId);


		
 	           
	 
 
		
		
		
		
		
		
	}
	
	
	
	 public Contractor  saveContractor(String name,int typeId,int ownerId) throws DataBaseException {
			
		Contractor contractor=new Contractor();
		contractor.setName(name);
		contractor.setTypeId(typeId);
		contractor.setTypeName(String.valueOf(typeId));
		contractor.setOwnerId(ownerId);

			try {
				Map <String,Object>m=new HashMap<String,Object>();
				m.put("name", name);
				m.put("typeId",typeId);
				m.put("ownerId", ownerId);
				contractor=(Contractor) this.getBaseService().findAllBeans(Contractor.class, m, null).get(0);
				return contractor;
			} catch (DataBaseException | EmptyResultSetException e) {
				this.getBaseService().addBean(contractor);
			
			}
			
			return contractor;
			
		}
	 public ContractorAccount  findContractorAccount(int contractorId) throws DataBaseException {
			
		ContractorAccount contractorAccount=new ContractorAccount();
		contractorAccount.setId(contractorId);
		contractorAccount.setTotalAmount(0.0);

			try {
				Map <String,Object>m=new HashMap<String,Object>();
				m.put("contractorId", contractorId);
			
				contractorAccount=(ContractorAccount) this.getBaseService().findAllBeans(ContractorAccount.class, m, null).get(0);
				return contractorAccount;
			} catch (DataBaseException | EmptyResultSetException e) {
				this.getBaseService().addBean(contractorAccount);
			
			}
			
			return contractorAccount;
			
		}
@Override
	 public List<String> getSuggestedContractorName(String searchString,int ownerId,int typeId) {
		 return this.contractorDao.getSuggestedContractorName(searchString, ownerId, typeId);
	 }
@Override
public List getContractorAccount(String name, int seasonId, int typeId, Date fromDate, Date toDate, int paid,
		int ownerId) throws DataBaseException, EmptyResultSetException {
	// TODO Auto-generated method stub
	return this.getContractorDao().getContractorAccount(name, seasonId, typeId, fromDate, toDate, paid, ownerId);
}

	
}
