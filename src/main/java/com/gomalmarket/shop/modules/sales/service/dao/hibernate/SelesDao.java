package com.gomalmarket.shop.modules.sales.service.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.sales.service.dao.ISalesDao;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Repository
public class SelesDao  implements  ISalesDao{
	
	  @PersistenceContext
	    EntityManager entityManger;
	public Object  aggregate(String tablename,String operation,String columnName,Map <String,Object>parameters) throws DataBaseException, EmptyResultSetException {
		
		
		
	
		  try { 
		String query="select "+operation+"("+columnName+") from "+tablename+ " where 1=1";
		
	if(parameters!=null)
	     for (Map.Entry entry :	parameters.entrySet())  {

	    	 query+=" and "+String.valueOf( entry.getKey()) +String.valueOf(entry.getValue());
	    }
	     
	        List result= entityManger.createQuery(query).getResultList();
	        
	        if(result.size() == 0) {
				  throw new  EmptyResultSetException("error.emptyRS"); }
			 
			  if(result.size() > 0) 
			  { return (result.get(0)==null)?0:result.get(0) ;}
	      
	        
		  }catch(DataAccessException e) {
			  throw new
			  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
			  }
			  finally {
				  
				  
			  }
		return null;
		  
		  
	}
	
	
	 public List<String> getSuggestedSellerName(String searchString) {

	        log.info("Getting seller list for autocomplete");

	        List<String> list = null;

 
 
	        try {

	        	 Query query =entityManger.createQuery("select s.name from Seller s where s.name LIKE :search");
	            

	            list = query.setParameter("search", "%"+searchString+"%").setMaxResults(10).getResultList();


	        } catch (Exception ex) {

	            log.error("Other exception {}", ex);

	        } finally {

	        	 

	        }       

	        return list;

	    } 
	 
	 
	 
	 
	 public List getIncome(Date date) throws EmptyResultSetException, DataBaseException {
		 

			
		 
		  try { 
		 
			

		  
		  String q = "from Income "
		  		+ "where  to_char(  incomeDate ,'dd/MM/YYYY')  = "
		  		+ " to_char( :dataParam ,'dd/MM/YYYY') ";							

		  q += " order by incomeDate  desc";
			
		  
		  Query qurey = entityManger.createQuery(q);
		  qurey.setParameter("dataParam", date, TemporalType.DATE);
		//  queryList.setDate(1, upper.getTime());

		  List result =	 qurey.getResultList();
		  
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

	 public List getSellersOrders(Date orderDate) throws EmptyResultSetException, DataBaseException {
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

			 
			  try { 
				 
			 
			  String q =
			  "from SellerOrder "
				+ "where  to_char(  orderDate ,'dd/MM/YYYY')  = '"+sdf.format(orderDate)+"'";
		  				  q += " order by orderDate  desc";
			
			  
			  Query query = entityManger.createQuery(q);
			  //queryList.setDate(0, orderDate);

			  List result =	 query.getResultList();
			  
			  if(result.size() == 0) {
				  throw new  EmptyResultSetException("error.emptyRS"); }
			  
			  if(result.size() > 0) 
			  {return result;}
			 } 
			  catch(DataAccessException e) { throw new
			  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
			  }
			  finally {   }
			  
			 
			return null;}
		
		
	 public List getSellerDebt(int sellerId, int seasonId) throws EmptyResultSetException, DataBaseException {
			
	  
		  try { 
			 
		 
		  String query =
		  "from SellerLoanBag "
			+ " where season.id= :seasonId"
	  		+ " and seller.id= :sellerId";
		  query += " order by sellerI.id  desc";
		
		  
		  Query queryList = entityManger.createQuery(query);
		  queryList.setParameter("seasonId", seasonId);
		  queryList.setParameter("sellerId", sellerId);

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
	
	 public List getSellersDebts( int seasonId,int active) throws EmptyResultSetException, DataBaseException {
			
	 
		  try { 
	 
		 
		  String query =
		  "from SellerLoanBag "
			+ " where season.id = :seasonId";
 
		  query+=(active==0)?"  and currentLoan=0 ":" and currentLoan>0  ";
		  query += " order by seller.id  desc";
		  
		  
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
public List getSellersLoanSummary(Date fromDate,Date toDate,int seasonId) throws EmptyResultSetException, DataBaseException {
	
	
	 

	  try { 
		 String sql=" select    \r\n" + 
		 		"                      SLB.PRIOR_LOAN,    \r\n" + 
		 		"                       \r\n" + 
		 		"                      nvl(( select sum( SO.TOTAL_COST )from seller_orders  so where SO.SELLER_LOAN_BAG_ID =slb.id \r\n" + 
		 		"                          and SO.ORDER_DATE between :fromDate and :toDate   ),0)as TOTAL_COST ,  \r\n" + 
		 		"                   \r\n" + 
		 		"                      nvl((select sum(INST.AMOUNT )from SHOP2020.INSTALLMENTS inst    where INST.SELLER_LOAN_BAG_ID =slb.id  \r\n" + 
		 		"                          and INST.INSTALLMENT_DATE between :fromDate and :toDate   ),0)as AMOUNT ,     \r\n" + 
		 		"                          SLB.CURRENT_LOAN  ,S.NAME   \r\n" + 
		 		"                      from seller_loan_bagS slb  , sellerS s    \r\n" + 
		 		"                      where SLB.SEASON_ID=:seasonId "
		 		+ "	 and slb.CHANGE_DATE between :fromDate and :toDate"
		 		+ ""
		 		+ "  \r\n" + 
		 		"                      and SLB.CURRENT_LOAN>0  and s.id=SLB.SELLER_ID    \r\n" + 
		 		"                      group by SLB.SELLER_ID,slb.PRIOR_LOAN,slb.id,SLB.CURRENT_LOAN,S.NAME\r\n" + 
		 		"                        order by CURRENT_LOAN desc ";

		 Query  query = entityManger.createNativeQuery(sql);
	  query.setParameter("fromDate", fromDate,TemporalType.DATE);
	  query.setParameter("toDate", toDate,TemporalType.DATE);
	  query.setParameter("seasonId", seasonId);

	   

	  List result = query.getResultList();
 
	
	
	  if(result.size() == 0) {
		  throw new  EmptyResultSetException("error.emptyRS"); }
	  
	  if(result.size() > 0) 
	  {return result;}
	 } 
	  catch(DataAccessException e) { throw new
	  DataBaseException("error.dataBase.query,SellersLoanSummary,"+e.getMessage()  );
	  }
	  finally { }
	  
	 
	return null;}
	
	
	
	
 
}
	
	
	
	
	
	
	
	
	
	

