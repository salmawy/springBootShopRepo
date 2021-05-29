package com.gomalmarket.shop.modules.expanses.services.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.safe.SafeOfDay;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.expanses.enums.SafeTypeEnum;
import com.gomalmarket.shop.modules.expanses.services.dao.IExpansesDao;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Repository
@Component
public class ExpansesDao  implements  IExpansesDao{

    @PersistenceContext
    EntityManager entityManger;

	
	
	public List getIncome(Date date) throws EmptyResultSetException, DataBaseException {
		 
		  try { 
		  
		  String query = "from Income "
		  		+ "where  to_char(  incomeDate ,'dd/MM/YYYY')  = "
		  		+ " to_char( :date_P,'dd/MM/YYYY') ";							

		  query += " order by incomeDate  desc";
			
		  
		  Query queryList = entityManger.createQuery(query);
		  queryList.setParameter("date_P", date,TemporalType.DATE);
		//  queryList.setDate(1, upper.getTime());

		  List<Object> result =	 queryList.getResultList();
		  
		  if(result.size() == 0) {
			  throw new  EmptyResultSetException("error.emptyRS"); }
		  
		  if(result.size() > 0) 
		  {return result;}
		 } 
		  catch(DataAccessException e) { throw new
		  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
		  }
		  finally {  }
		  
		 
		return null;
		} 

	
	@Override
	public double getSafeBalanceOfday(int seasonId,Date date,SafeTypeEnum type) {
		double balance =0.0;
		String queryString =" select sum(balance)  from  SafeOfDay sod where 1=1 "
				+ " and sod.season.id = :seasonId ";
				
		
		if(type==SafeTypeEnum.OPENNNING_BALANCE)
			queryString+= " and to_char( sod.dayDate,'YYYYMMDD') <  to_char(:dayDate,'YYYYMMDD')  ";
		if(type==SafeTypeEnum.CURRENT_BALANCE)
			queryString+= " and  to_char( sod.dayDate,'YYYYMMDD') <=  to_char(:dayDate,'YYYYMMDD')  ";

		
		
		Query query= entityManger.createQuery(queryString)
		.setParameter("seasonId", seasonId)
		.setParameter("dayDate", date,TemporalType.DATE);
		
			List result =query.getResultList();
	if(result.size()>0) {
		return (Double)result.get(0);
	}
		
		return balance;
		
	}
	 
	 public List getIncomeDays(String month) throws EmptyResultSetException, DataBaseException {
		 
		  try { 
		  String query = "from Income "
		  		+ "where  to_char(  incomeDate ,'YYYY-MM')  = :month";

		  query += " order by incomeDate  desc";
			
		  
		  Query queryList = entityManger.createQuery(query);
		  queryList.setParameter("month", month);

		  List result =	 queryList.getResultList();
		  
		  if(result.size() == 0) {
			  throw new  EmptyResultSetException("error.emptyRS"); }
		  
		  if(result.size() > 0) 
		  {return result;}
		 } 
		  catch(DataAccessException e) { throw new
		  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
		  }
		  finally {   }
		  
		 
		return null;
		 
		 
		 
	 } 

	
	 
	 public List getIncomeMonthes(int seasonId) throws EmptyResultSetException, DataBaseException {
		 

			
		  
		  try { 
			 
			

		  
			String sql=" select distinct to_char(income0_.INCOME_DATE, 'YYYY-MM') as mon from INCOME income0_ where income0_.SEASON_ID="+seasonId+" order by mon desc";
				
		  Query queryList = entityManger.createNativeQuery(sql);

		  
		  List  result =	 queryList.getResultList();
		  
		  if(result.size() == 0) {
			  throw new  EmptyResultSetException("error.emptyRS"); }
		  
		  if(result.size() > 0) 
		  {return result;}
		 } 
		  catch(DataAccessException e) { throw new
		  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
		  }
		  finally {   }
		  
		 
		return null;
		 
		 
		 
	 } 

	
	 
	 @Override
	 public List getIncomeDates(int seasonId) throws EmptyResultSetException, DataBaseException {
		 

			
		 
		  try { 
			 
			

		  
			String hql="  from Income where season.id="+seasonId+" order by incomeDate desc";
				
		  Query queryList = entityManger.createQuery(hql);

		  
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
		  
		 
		return null;
		 
		 
		 
	 } 

	
	 
	 public List getOutcomeMonthes(int seasonId) throws EmptyResultSetException, DataBaseException {
		 

			
		  
		  try { 
			  
			

		  
				String sql=" select distinct to_char(OUTCOME_DATE, 'YYYY-MM') as mon from OUTCOME  where SEASON_ID="+seasonId+" order by mon desc";

			
		  
		  Query queryList = entityManger.createNativeQuery (sql);

		  
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
		  
		 
		return null;
		 
		 
		 
	 } 

	 
	 public List getOutcomeDays(String month) throws EmptyResultSetException, DataBaseException {
		 

			
 		  try { 
 			

		  
		  String query = "from Outcome "
		  		+ "where  to_char(  outcomeDate ,'YYYY-MM')  = :month";

		  query += " order by outcomeDate  desc";
			
		  
		  Query queryList = entityManger.createQuery(query);
		  queryList.setParameter("month", month);

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
		  
		 
		return null;
		 
		 
		 
	 } 

	

	
	
	 
	 
	 public List getOutcome(Date date) throws EmptyResultSetException, DataBaseException {
		 

			
		 
		  try { 
 			

		  
		  String query = "from Outcome "
		  		+ "where  to_char(  outcomeDate ,'dd/MM/YYYY')  = "
		  		+ " to_char(:date_P ,'dd/MM/YYYY') ";							

		  query += " order by outcomeDate  desc";
			
		  
		  Query queryList = entityManger.createQuery(query);
		  queryList.setParameter ("date_P", date,TemporalType.DATE);
		//  queryList.setDate(1, upper.getTime());

		  List<Object> result =	 queryList.getResultList();
		  
		  if(result.size() == 0) {
			  throw new  EmptyResultSetException("error.emptyRS.Outcome"); }
		  
		  if(result.size() > 0) 
		  {return result;}
		 } 
		  catch(DataAccessException e) { throw new
		  DataBaseException("error.dataBase.query,Outcome,"+e.getMessage()  );
		  }
		  finally { }
		  
		 
		return null;
		 
		 
		 
	 } 

	@Override
	 public List getSafeOfDay(Date date) throws EmptyResultSetException, DataBaseException {
		 

			
		 
		  try { 
			

		  
		  String query = "from SafeOfDay  "
		  		+ "where  to_char(  dayDate ,'dd/MM/YYYY')  = "
		  		+ " to_char(:date_P ,'dd/MM/YYYY') ";							

		  query += " order by dayDate  desc";
			
		  
		  Query queryList = entityManger.createQuery(query);
		  queryList.setParameter ("date_P", date,TemporalType.DATE);
 
		  List<Object> result =	 queryList.getResultList();
		  
		  if(result.size() == 0) {
			  throw new  EmptyResultSetException("error.emptyRS,SafeOfDay"); }
		  
		  if(result.size() > 0) 
		  {return result;}
		 } 
		  catch(DataAccessException e) { throw new
		  DataBaseException("error.dataBase.query,SafeOfDay ,"+e.getMessage()  );
		  }
		  finally { }
		  
		 
		return null;
		 
		 
		 
	 } 

	


	
	
	
	
	
	
	
	

public List getLoanerDebts(int loanerId, String type) throws EmptyResultSetException, DataBaseException {
	 

	
	 
	  try { 
		  
		

	  
	  String query = "from IncLoan il "
	  		+ "where il.loanAccount.loaner.id= :loanerId  "
	  		+ " and  il.loanAccount.type= :type "
	  		+ " and  il.loanAccount.finished= 0 ";							


	  query += " order by loanDate  desc";
		
	  
	  Query queryList = entityManger.createQuery(query);
	  queryList.setParameter("loanerId", loanerId);
	  queryList.setParameter("type", type);

	  List<Object> result =	 queryList.getResultList();
	  
	  if(result.size() == 0) {
		  throw new  EmptyResultSetException("error.emptyRS"); }
	  
	  if(result.size() > 0) 
	  {return result;}
	 } 
	  catch(DataAccessException e) { throw new
	  DataBaseException("error.dataBase.query,LoanerDebts,"+e.getMessage()  );
	  }
	  finally { }
	  
	 
	return null;
	 
	 
	 
}



public List getLoanerInstalments(int loanerId, String type) throws EmptyResultSetException, DataBaseException {
	 

	
 	  try { 
 		

	  
	  String query = "from LoanPaying lp "
	  		+ "where lp.loanAccount.loaner.id= :loanerId  "
	  		+ " and  lp.loanAccount.type= :type "
	  		+ " and  lp.loanAccount.finished= 0 ";							


	  query += " order by payingDate  desc";
		
	  
	  Query queryList = entityManger.createQuery(query);
	  queryList.setParameter("loanerId", loanerId);
	  queryList.setParameter("type", type);

	  List<Object> result =	 queryList.getResultList();
	  
	  if(result.size() == 0) {
		  throw new  EmptyResultSetException("error.emptyRS"); }
	  
	  if(result.size() > 0) 
	  {return result;}
	 } 
	  catch(DataAccessException e) { throw new
	  DataBaseException("error.dataBase.query,LoanerDebts,"+e.getMessage()  );
	  }
	  finally { }
	  
	 
	return null;
	 
	 
	 
}


 @Override
public List inExactMatchSearchloanerName(String loanerName, String loanerType) throws EmptyResultSetException, DataBaseException {

   
 	  try { 
 		
     
        String  query =  " select distinct loaner.name from LoanAccount  "
        		+ " where type= :loanerType"
        		+ "	and loaner.name like '%"+loanerName+" %' ";
        		
        		
  	  Query queryList = entityManger.createQuery(query);
  	  queryList.setParameter("loanerType", loanerType);
  	 // queryList.setParameter("name", loanerName);

  	  List<Object> result =	 queryList.getResultList();
  	  
  	  if(result.size() == 0) {
  		  throw new  EmptyResultSetException("error.emptyRS"); }
  	  
  	  if(result.size() > 0) 
  	  {return result;}
  	 } 
  	  catch(DataAccessException e) { throw new
  	  DataBaseException("error.dataBase.query,LoanerDebts,"+e.getMessage()  );
  	  }
  	  finally { }
  	  
  	 
  	return new ArrayList();
  	 
  	 
    
 
}


	
	
}
