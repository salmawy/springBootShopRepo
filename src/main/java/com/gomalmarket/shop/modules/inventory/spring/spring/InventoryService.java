package com.gomalmarket.shop.modules.inventory.spring.spring;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;
import com.gomalmarket.shop.modules.inventory.dao.IInventoryDao;
import com.gomalmarket.shop.modules.inventory.spring.IInventoryService;
import com.gomalmarket.shop.modules.sales.service.dao.ISalesDao;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;




@Service
@Transactional
@Getter
@Slf4j
public class InventoryService implements IInventoryService {
	
	private ISalesDao salesDao;
 	private IExpansesServices expansesServices;
	private IInventoryDao  inventoryDao ;


	IBaseService baseService;
 
 
 	private Map<String,Object>entityDictionary;
	public Map<String,Object> getEntityDictionary() {
		return entityDictionary;
	}
	public void setEntityDictionary(Map<String,Object> entityDictionary) {
		this.entityDictionary = entityDictionary;
	}
 
	@Override
	public List getInventoryDates(int seasonId) throws EmptyResultSetException, DataBaseException{
		
		return getInventoryDao().getInventoryDates(seasonId);
		
	}
	
	
	
	
	
	
	
	
	

	@Override
	public double getPurchasesProfit(String month, int seasonId, int fridageId) {
		// TODO Auto-generated method stub
		return getInventoryDao().getPurchasesProfit(month, seasonId, fridageId);
	}
	@Override
	public double getCommisionProfit(String month, int seasonId, int fridageId) {
		// TODO Auto-generated method stub
		return getInventoryDao().getCommisionProfit(month, seasonId, fridageId);
	}
	@Override
	public double getKTotalOrders(String month, int seasonId, int fridageId) {
		// TODO Auto-generated method stub
		return getInventoryDao().getKTotalOrders(month, seasonId, fridageId);
	}
	@Override
	public double getkaremmTotalWithdrawal(String month, int seasonId, int fridageId, int contractorType) {
		// TODO Auto-generated method stub
		return getInventoryDao().getkaremmTotalWithdrawal( month,  seasonId,  fridageId,  contractorType);
	}
	@Override
	public double getTotalOutcome(String month, int seasonId, int fridageId) {
		// TODO Auto-generated method stub
		return getInventoryDao().getTotalOutcome(month, seasonId, fridageId);
	}
	@Override
	public double getSalamiProductsProfit(String month, int seasonId, int fridageId, int productId) {
		// TODO Auto-generated method stub
		return getInventoryDao().getSalamiProductsProfit( month,  seasonId,  fridageId,  productId);
	}

    }
	
	
	
	
	
	
	
	
	
	 
 
