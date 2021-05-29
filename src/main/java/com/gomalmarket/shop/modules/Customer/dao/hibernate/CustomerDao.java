package com.gomalmarket.shop.modules.Customer.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

 import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.Enum.VechileTypeEnum;
import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.Customer.dao.ICustomerDao;

import lombok.extern.slf4j.Slf4j;


@Repository
@Transactional
@Slf4j
public class CustomerDao   implements  ICustomerDao{
	@PersistenceContext
	EntityManager entityManager;

	public List getSeasonCustomers(Season season,Fridage fridage,int typeId) throws EmptyResultSetException, DataBaseException {
		
	 
		  try { 
		 
		  
		  String query =
		  " select distinct  co.customer   from CustomerOrder co "
		 
		  + "	 where co.season.id= "+season.getId();
		  query +="	and co.customer.type.id ="+typeId;
		  
		  if(fridage!=null)
		  query +="	and co.fridage.id ="+fridage.getId();

		    Query queryList = entityManager.createQuery(query);

		  List<Object> result =queryList.getResultList();
		  
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
	
	public List getCustomerOrders(Date orderDate) throws EmptyResultSetException, DataBaseException {
		
 		  try { 
 		  
		  String query =
		  "from CustomerOrder where to_char(orderDate,'DD/MM/YYYY') = to_char(:orderDate_P,'DD/MM/YYYY')";

		  query += " order by orderDate  desc";
		 

		  
		  Query queryList = entityManager.createQuery(query);
		  queryList.setParameter("orderDate_P", orderDate,TemporalType.DATE);
		
		  List<Object> result =		  queryList.getResultList();
		  
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
	
	
	
	public List getCustomerOrders(int customerId,Season season,Fridage fridage ,int finished) throws EmptyResultSetException, DataBaseException {		
 		  try { 
 		  
		  String query =
		  "from CustomerOrder where 1=1";
		  if(customerId!=0)
				 query +=" and customer.id = "+customerId;
		  if(season!=null)
			query+=" and season.id="+season.getId();
		
		  if(fridage!=null)
				 query +=" and fridage.id = "+fridage.getId();
		  if(finished!=0)
			  query+=" and  finished ="+finished;
		
		 
		
		
		 query += " order by orderDate  desc";
	
		 
		 
		
		
		  List<Object> result =entityManager.createQuery(query).getResultList() ; 
		  
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
	
	public double  getPurchasedCustomerTotalDue(Season season,int customerId) throws EmptyResultSetException, DataBaseException {
		
 		  try { 
 		  
		  String query =" select sum (buyPrice)"+
		  "from CustomerOrder where season.id="+season.getId()
		 +" and  periodId =-1";
		
		 if(customerId!=0)
			 query +=" and customer.id = "+customerId;

		
		 query += " order by orderDate  desc";
	
		 
		 
		
		
		  List<Object> result =entityManager.createQuery(query).getResultList() ; 
		  
		  if(result.size() == 0) {
			  throw new  EmptyResultSetException("error.emptyRS"); }
		  
		  if(result.size() > 0) 
		  {return (Double)result.get(0);}
		 } 
		  catch(DataAccessException e) { throw new
		  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
		  }
		  finally { }
		  
		 
		return 0.0;}
	
	public double getPurchasedCustomerTotalInst(Season season,int customerId) throws EmptyResultSetException, DataBaseException {
		
 		  try { 
 		  
		  String query =" select sum (amount)"+
		  "from PurchasedCustomerInst "
		  + "where season.id="+season.getId();
		
		 if(customerId!=0)
			 query +=" and customer.id = "+customerId;

		
		 query += " order by instalmentDate  desc";
	
		 
		 
		
		
		  List<Object> result =entityManager
				  .createQuery(query).getResultList() ; 
		  
		  if(result.size() == 0) {
			  throw new  EmptyResultSetException("error.emptyRS"); }
		  
		  if(result.size() > 0) 
		  {return (Double)result.get(0);}
		 } 
		  catch(DataAccessException e) { throw new
		  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
		  }
		  finally { }
		  
		 
		return 0.0;}


	
	

	
	
	
	
	public Season getCurrentSeason() throws DataBaseException, EmptyResultSetException {
		

		
 		  try { 
 		  
		  String query =
		  "from Season where currentSeason = 1";
		  Query queryList = entityManager.createQuery(query);
		
		  List<Object> result =		  queryList.getResultList();
		  
		  if(result.size() == 0) {
			  throw new  EmptyResultSetException("error.emptyRS"); }
		  
		  if(result.size() > 0) 
		  {return (Season) result.get(0);}
		 } 
		  catch(DataAccessException e) { throw new
		  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
		  }
		  finally { }
		  
		 
		return null;
		
		
	}



	public List getCustomersSummaryTransactions(Season season,Fridage fridage,int customerId) throws EmptyResultSetException, DataBaseException {
		
 		  try { 
 		  
	


		  String q ="select"
		  		+ " sum (od.amount) ,"
		  		+ "sum(co.netPrice),"
		  		+ "sum(co.commision)"
		  		+ " from "
		  		+ " CustomerOrder co , "
		  		+ " OutcomeDetail od "
		  		+ " where co.id=od.orderId"	
		        + "	 and  co.finished= 1"
		  		+ "	 and co.season.id= "+season.getId() ;
		  if(customerId!=0)
			  q +="	and co.customer.id ="+customerId;
		  if(fridage!=null)
			  q +="	and co.fridage.id ="+fridage.getId();
		
		  
		  
		  
		  
		  String vechilrTypesQuery =
				  " select "
				  + "    count (co.vehicleType.id), co.vehicleType.id"
				
				  + " from CustomerOrder co  "
				  + "	 where co.season.id= "+season.getId() ;
				  if(customerId!=0)
					  vechilrTypesQuery +="	and co.customer.id ="+customerId;
				  if(fridage!=null)
					  vechilrTypesQuery +="	and co.fridage.id ="+fridage.getId();
				  
				  vechilrTypesQuery +="	group by  co.vehicleType.id order by co.vehicleType.id asc ";

		  
		  
	
		  
		  
		  
		  
		  Query queryList = entityManager.createQuery(q);

		  List result =queryList.getResultList();
		   queryList = entityManager.createQuery(vechilrTypesQuery);

		  List result2 =queryList.getResultList();
		  Map<Object,Object>m=new HashMap<Object, Object>();
		  m.put(VechileTypeEnum.van, (long) 0);
		  m.put(VechileTypeEnum.car, (long) 0);
		
		  for (int i = 0; i < result2.size(); i++) {
			  Object[] temp=(Object[]) result2.get(i);
			  m.put(temp[1], temp[0]);
			
		}
		  
		  
		  long vanCount=(long) m.get(VechileTypeEnum.van);
		  long carCount=(long) m.get(VechileTypeEnum.car);
		  List finalResult=new ArrayList<Object>(Arrays.asList( ((Object[])result.get(0))[0],
				  ((Object[])result.get(0))[1],
				  ((Object[])result.get(0))[2],
				  vanCount,carCount ));
		
		  if(finalResult.size() == 0) {
			  throw new  EmptyResultSetException("error.emptyRS"); }
		  
		  if(finalResult.size() > 0) 
		  {return finalResult;}
		 } 
		  catch(DataAccessException e) { throw new
		  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
		  }
		  finally { }
		  
		 
		return null;}
	
	
	
	 public List<String> getSuggestedCustomerName(String searchString,int customerTypeId) {

 
	        List<String> list = null;

 
 
	        try {

 	            Query query = entityManager.createQuery("select c.name from Customer c where c.name LIKE :search and type.id = :typeP");
	             query.setParameter("typeP", customerTypeId);

	            list = query.setParameter("search", "%"+searchString+"%").setMaxResults(10).getResultList();

 	        } catch (Exception ex) {

	            log.error("Other exception {}", ex);

	        } finally {

 
	        }       

	        return list;

	    } 
	 
	 
	 
	 public List getOutcome(Date date) throws EmptyResultSetException, DataBaseException {
		 

			
 		  try { 
 			

		  
		  String query = "from Outcome "
		  		+ "where  to_char(  outcomeDate ,'dd/MM/YYYY')  = "
		  		+ " to_char( :orderDate_P ,'dd/MM/YYYY') ";							

		  query += " order by outcomeDate  desc";
			
		  
		  Query queryList = entityManager.createQuery(query);
		  queryList.setParameter ("orderDate_P", date,TemporalType.DATE);
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
		  finally { }
		  
		 
		return null;
		 
		 
		 
	 }

 

}
