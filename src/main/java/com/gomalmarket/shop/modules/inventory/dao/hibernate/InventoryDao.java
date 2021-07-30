package com.gomalmarket.shop.modules.inventory.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.Enum.CustomerTypeEnum;
import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.inventory.dao.IInventoryDao;

import lombok.extern.slf4j.Slf4j;



@Transactional
@Repository
@Slf4j
public class InventoryDao   implements  IInventoryDao{ 
	
	@Autowired
	ShopAppContext shopAppContext;
	
	@PersistenceContext
	EntityManager entityManger;
	
	
	
	
	@Override
 public List getInventoryDates( int seasonId) throws EmptyResultSetException, DataBaseException {
		
 	  try { 
 	 
	  String query =
	  " select distinct to_char( orderDate,'MM-YYYY') from customerDate "
		+ " where seasonId :seasonId ";
	  
	  
 	  query += " order by orderDate  desc";
	
	  
	  Query queryList = entityManger.createQuery(query);
	  queryList.setParameter("seasonId", seasonId);
 
	  List<Object> result =	 queryList.getResultList();
	  
	  if(result.size() == 0) {
		  throw new  EmptyResultSetException("error.emptyRS"); }
	  
	  if(result.size() > 0) 
	  {return result;}
	 } 
	  catch(DataAccessException e) { throw new
	  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
	  }
	  finally { }
	  
	 
	return null;}


	@Override
 public double getPurchasesProfit(String month, int seasonId, int fridageId) {
	 
	 double value=0.0;
	 
	 
		
 	  try { 
 	 
	  String query =
	  " select sum(commision) from CustomerOrder"
	  + " where periodId=-1"
	  + " and finished=1"
	  + " and buyPrice>0 "
	  + " and seasonId  ="+seasonId;
	  if(fridageId!=0)
		  query+=" and fridageId ="+fridageId;
	  if(month!=null)
		  query+=" and to_char(orderDate,'MM-YYYY') ="+month;
	  
	  
 	  query += " order by orderDate  desc";
	
	  
	  Query queryList = entityManger.createQuery(query);
	  queryList.setParameter("seasonId", seasonId);
 
	  List result =	 queryList.getResultList();
	  
  	  
	  if(result.size() > 0) 
	  value= (double) result.get(0);
	 } 
	  catch(Exception e) {
  	  }
	  finally { }
	  
	 
	
	 
	 return value;
 }
 public double getCommisionProfit(String month, int seasonId, int fridageId) {

		 
		 double value=0.0;
		 
		 
			
 		  try { 
 		 
		  String query =
		  " select sum(commision) from CustomerOrder"
		  + " where periodId=-1"
		  + " and finished=1"
		  + " and buyPrice=0 "
		  + " and seasonId  ="+seasonId;
		  if(fridageId!=0)
			  query+=" and fridageId ="+fridageId;
		  if(month!=null)
			  query+=" and to_char(orderDate,'MM-YYYY') ="+month;
		  
		  
	 	  query += " order by orderDate  desc";
		
		  
		  Query queryList = entityManger.createQuery(query);
		  queryList.setParameter("seasonId", seasonId);
	 
		  List result =	 queryList.getResultList ();
		  
	  	  
		  if(result.size() > 0) 
		  value= (double) result.get(0);
		 } 
		  catch(Exception e) {
	  	  }
		  finally { }
		  
		 
		
		 
		 return value;
	 
		 
	 }
	 @Override
 public double getKTotalOrders(String month, int seasonId, int fridageId) {

	 
	 double value=0.0;
	 
	 
		
 	  try { 
 	 
	  String query =
	  " select sum(netPrice) from CustomerOrder"
	  + " where customer.typeId= "+CustomerTypeEnum.kareem
	  + " and finished=1"
	 
	  + " and seasonId  ="+seasonId;
	  if(fridageId!=0)
		  query+=" and fridageId ="+fridageId;
	  if(month!=null)
		  query+=" and to_char(orderDate,'MM-YYYY') ="+month;
	  
	  
 	  query += " order by orderDate  desc";
	
	  
	  Query queryList = entityManger.createQuery(query);
	  queryList.setParameter("seasonId", seasonId);
 
	  List result =	 queryList.getResultList();
	  
  	  
	  if(result.size() > 0) 
	  value= (double) result.get(0);
	 } 
	  catch(Exception e) {
  	  }
	  finally { }
	  
	 
	
	 
	 return value;
 
	 
 }
  @Override
 public double getkaremmTotalWithdrawal(String month, int seasonId, int fridageId,int contractorType) {

	 
	 double value=0.0;
 
 	  try { 
 	 
	  String query =
	  " select sum(amount) from ContractorTransaction"
	  + " where seasonId= "+seasonId
	  + " and paid=1";
	 
 	  if(month!=null)
		  query+=" and to_char(detailDate,'MM-YYYY') ="+month;
	  
	  
 	  query += " order by orderDate  desc";
	
	  
	  Query queryList = entityManger.createQuery(query);
	  queryList.setParameter("seasonId", seasonId);
 
	  List result =	 queryList.getResultList();
	  
  	  
	  if(result.size() > 0) 
	  value= (double) result.get(0);
	 } 
	  catch(Exception e) {
  	  }
	  finally { }
	  
	 
	
	 
	 return value;
 
	 
 }
 @Override
 public double getTotalOutcome(String month, int seasonId, int fridageId) {

	 
	 double value=0.0;
		 
	              
 	  try { 
 	 
	  String query =
	  " select sum(amount) from outcomeDetail"
	  + " where seasonId= "+seasonId
	  + " and paid=1"
	  + " and typeId  in ("
	  +OutcomeTypeEnum.labours+","
	  +OutcomeTypeEnum.varaity+","
	  +OutcomeTypeEnum.maintaince+","
	  +OutcomeTypeEnum.forgivness+","
	  +OutcomeTypeEnum.allah+")";
 	  if(month!=null)
		  query+=" and to_char(detailDate,'MM-YYYY') ="+month;
	  
	  
 	  query += " order by orderDate  desc";
	
	  
	  Query queryList = entityManger.createQuery(query);
	  queryList.setParameter("seasonId", seasonId);
 
	  List result =	 queryList.getResultList();
	  
  	  
	  if(result.size() > 0) 
	  value= (double) result.get(0);
	 } 
	  catch(Exception e) {
  	  }
	  finally { }
	  
	 
	
	 
	 return value;
 
	 
 }


 


      @Override
 public double getSalamiProductsProfit(String month, int seasonId, int fridageId,int productId) {
         

	 


	 
	 double value=0.0;
		 
	              
 	  try { 
 	 
	  String query =
	  " select sum(amount) from SellerOrderWeight"
	  + " where seasonId= "+seasonId
	  + " and productId="+productId;
	  if(fridageId!=0)
		  query+=" and sellerOrder.fridageId ="+fridageId;
 	  if(month!=null)
		  query+=" and to_char(detailDate,'MM-YYYY') ="+month;
	  
	  
 	  query += " order by orderDate  desc";
	
	  
	  Query queryList = entityManger.createQuery(query);
	  queryList.setParameter("seasonId", seasonId);
 
	  List result =	 queryList.getResultList();
	  
  	  
	  if(result.size() > 0) 
	  value= (double) result.get(0);
	 } 
	  catch(Exception e) {
  	  }
	  finally { }
	  
	 
	
	 
	 return value;
 
	 
 
	 
	 
	 
	 
	 
 

	 
	 
}
	
	
}
	
	
	
	
	
	
	

