package com.gomalmarket.shop.modules.shopLoans.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.shopLoans.dao.IShoploanDao;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository
@Slf4j

public class ShopLoanDao implements IShoploanDao {

	@PersistenceContext
	EntityManager entityManger;

	@Override
	public List getLoaners(LoanTypeEnum type) throws EmptyResultSetException, DataBaseException {

		try {

			String queryString = " select acc from LoanAccount acc " + " 	 inner join acc.loaner loaner "
					+ "	 	where acc.finished=0 " + "		and type like :type ";

			Query query = entityManger.createQuery(queryString);
			query.setParameter("type", type.getId());
			List<Object> result = query.getResultList();

			if (result.size() == 0) {
				throw new EmptyResultSetException("error.emptyRS.LoanAccount");
			}

			if (result.size() > 0) {
				return result;
			}
		} catch (DataAccessException e) {
			throw new DataBaseException("error.dataBase.query,LoanAccount," + e.getMessage());
		} finally {
		}

		return null;
	}

	@Override
	public List getLoans(int accountId) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub

		try {

			String queryString = " select incLoan from IncLoan incLoan	 	where incLoan.loanAccount.id=:accountId ";

			Query query = entityManger.createQuery(queryString);
			query.setParameter("accountId", accountId);
			List<Object> result = query.getResultList();

			if (result.size() == 0) {
				throw new EmptyResultSetException("error.emptyRS.LoanAccount");
			}

			if (result.size() > 0) {
				return result;
			}
		} catch (DataAccessException e) {
			throw new DataBaseException("error.dataBase.query,LoanAccount," + e.getMessage());
		} finally {
		}

		return null;

	}

	@Override
	public List getLoanerInst(int accountId) throws EmptyResultSetException, DataBaseException {
		// TODO Auto-generated method stub

		try {

			String queryString = " select loanPay from LoanPaying loanPay	 	where loanPay.loanAccount.id=:accountId ";

			Query query = entityManger.createQuery(queryString);
			query.setParameter("accountId", accountId);
			List<Object> result = query.getResultList();

			if (result.size() == 0) {
				throw new EmptyResultSetException("error.emptyRS.LoanAccount");
			}

			if (result.size() > 0) {
				return result;
			}
		} catch (DataAccessException e) {
			throw new DataBaseException("error.dataBase.query,LoanAccount," + e.getMessage());
		} finally {
		}

		return null;

	}

	@Override
	public double loanBalance(LoanTypeEnum type) throws EmptyResultSetException, DataBaseException {

		// TODO Auto-generated method stub

		try {

			String queryString = " select sum(acc.dueAmount) from LoanAccount acc	 "
					+ "	where acc.finished=0 and acc.type=:type ";

			Query query = entityManger.createQuery(queryString);
			query.setParameter("type", type);
			List<Object> result = query.getResultList();

			if (result.size() == 0) {
				throw new EmptyResultSetException("error.emptyRS.LoanAccount");
			}

			if (result.size() > 0) {
				return (double) result.get(0);
			}
		} catch (DataAccessException e) {
			throw new DataBaseException("error.dataBase.query,LoanAccount," + e.getMessage());
		} finally {
		}

		return 0.0;

	
	}

	
	
	
	 @Override
	 public List inExactMatchSearchloanerName(String loanerName, LoanTypeEnum loanerType) throws EmptyResultSetException, DataBaseException {

	    
	  	  try { 
	  		
	      
	         String  query =  " select distinct loaner.name from LoanAccount  "
	         		+ " where type= :loanerType"
	         		+ "	and loaner.name like '%"+loanerName+" %' ";
	         		
	         		
	   	  Query queryList = entityManger.createQuery(query);
	   	  queryList.setParameter("loanerType", loanerType.getId());
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

	
	
}