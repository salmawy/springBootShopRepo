package com.gomalmarket.shop.modules.contractor.dao.hibernate;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.contractor.dao.IContractorDao;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j

public class ContractorDao   implements IContractorDao{
@PersistenceContext
EntityManager entityManager;
	
	@Override
	public List getAllContractorsAccounts(int typeId,int ownerId,int seasonId) throws DataBaseException, EmptyResultSetException {
		
		 
 		  try { 	
 
		  String q =
		  		 " select ca from "
		  		+ " ContractorAccount ca  "
		  		+ " where   ca.contractor.typeId="+typeId
		  		 + " and    ca.contractor.ownerId="+ownerId
		  + " and    ca.seasonId="+seasonId;
		 
 
		  Query query= this.entityManager.createQuery(q);

		  List result =query.getResultList();

		
		  if(result.size() == 0) {
			  throw new  EmptyResultSetException("error.emptyRS"); }
		  
		  if(result.size() > 0) 
		  {return result;}
		 } 
		  catch(DataAccessException e) { throw new
		  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
		  }
		  finally {
			  
		  }
		  
		 
		return null;
		
	}
	
 
	
	
@Override
	 public List<String> getSuggestedContractorName(String searchString,int ownerId,int typeId) {

	        log.info("Getting contractor list for autocomplete");

	        List<String> list = null;

 
 
	        try {


	            Query query = entityManager.createQuery("select c.name from Contractor c where c.name LIKE :search "
	            		+ "	and c.typeId=:typeId"
	            		+ " and c.ownerId=:ownerId"
	            		+ "");
	            query.setParameter("typeId", typeId);
	            query.setParameter("ownerId", ownerId);
	            list = query.setParameter("search", "%"+searchString+"%").setMaxResults(10).getResultList();


	        } catch (Exception ex) {

	            log.error("Other exception {}", ex);

	        } finally {

 
	        }       

	        return list;

	    }




@Override
public List getContractorTransactions(String name, int typeId, Date fromDate, Date toDate, int paid,int ownerId) throws EmptyResultSetException, DataBaseException {

	
 	  try { 
 	  



	  String q ="select "
	  		+ " cad "
 	  		
	  		+ " from "
	  		+ " ContractorTransaction cad   "
	  		+ " where 1=1" ;
	  if(paid!=-1)
		  q +="	and cad.paid="+paid;
	  if( typeId!=0 )
		  q +="	and cad.contractor.typeId ="+typeId;
    if( ownerId!=0 )
			  q +="	and cad.contractor.ownerId ="+ownerId;
		  
	  if(toDate!=null&&fromDate!=null) {
		  q +="	and cad.transactionDate >= :fromDate";
		  q +="	and cad.transactionDate <= :toDate";
 
	  }
	  if(name!=null&&name!="")
		  q +="	and cad.contractor.name like  '%"+name+"%'";
	  
 
	  Query queryList = entityManager.createQuery(q);
	  if(toDate!=null&&fromDate!=null) {
		  queryList.setParameter("fromDate", fromDate,TemporalType.DATE);
		  queryList.setParameter("toDate", toDate,TemporalType.DATE);

		  
	  }
	  	  
	  
	  
	  List result =queryList.getResultList();

	
	  if(result.size() == 0) {
		  throw new  EmptyResultSetException("error.emptyRS"); }
	  
	  if(result.size() > 0) 
	  {return result;}
	 } 
	  catch(DataAccessException e) { throw new
	  DataBaseException("error.dataBase.query,ContractorTransaction,"+e.getMessage()  );
	  }
	  finally {
		  
	  }
	  
	 
	return null;
	

} 
	 
	 
	
	
	
	
	
	
	
	
	
	
	
	
}
