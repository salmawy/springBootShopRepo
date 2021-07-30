package com.gomalmarket.shop.core.service.dao.impl;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.exception.DataException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.JPAOrderBy;
import com.gomalmarket.shop.core.Enum.JPAOrderByEnum;
import com.gomalmarket.shop.core.entities.basic.Season;

/**
 * @author YeHia
 */

import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.dao.IBaseDao;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

@Repository
@Slf4j
@Transactional
public class BaseDaoImpl implements IBaseDao {


    @PersistenceContext
    EntityManager entityManger;


    @Override
    public List<Object> findAllBeansWithDepthMapping(Class<?> beanClass, Map<Object, Object> propertyMap)
            throws DataBaseException, EmptyResultSetException {


         try {
            String key, value;
            StringBuffer sb = new StringBuffer("");

            sb.append("from " + beanClass.getSimpleName() + " as bean");

            String andConditions = " where ";

            for (Iterator<Object> itr = propertyMap.keySet().iterator(); itr.hasNext(); ) {
                sb.append(andConditions);
                key = (String) itr.next();
                value = String.valueOf(propertyMap.get(key));
                sb.append(" bean." + key);
                if (!value.contains("is null") && !value.contains("is not null") && !value.contains(">") && !value.contains("<") && !value.contains("IN("))
                    sb.append("=");
                sb.append(" " + value + " ");
                if (!value.contains(" or"))
                    andConditions = " and ";
                else
                    andConditions = "  ";

            }

            //System.out.println(sb.toString());
            //	session= this.getSessionFactory().openSession();
            List<Object> result = entityManger.createQuery(sb.toString()).getResultList();

            if (result.size() == 0)
                throw new EmptyResultSetException("error.emptyRS," + beanClass.getSimpleName());

            return result;
        } catch (DataAccessException e) {
            throw new DataBaseException("error.dataBase.query," + beanClass.getSimpleName() + "," + e.getMessage());
        } finally {
           
        }

    }


    @Override
    @Transactional
    public List<Object> findAllBeans(Class<?> beanClass, Map<String, Object> params, List<JPAOrderBy> nOrder, int fromRecord, int maxResult) throws DataBaseException, EmptyResultSetException {
        List<Object> result = new ArrayList<Object>();

        try {

            CriteriaBuilder cb = entityManger.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(beanClass);
            List<Predicate> predicates = new ArrayList<>();
            log.info(beanClass.getName());
            Root root = cq.from(beanClass);
            if (params != null) {

                for (Iterator<String> itr = params.keySet().iterator(); itr.hasNext(); ) {
                    String key = itr.next();

                    if (params.get(key) == null)
                        predicates.add(cb.isNull(root.get(key)));
                    else if (params.get(key) instanceof String && ((String) params.get(key)).equals("NOT_NULL") == true)
                        predicates.add(cb.isNotNull(root.get(key)));
                    else
                        predicates.add(cb.equal(root.get(key), params.get(key)));
                }

                cq.where(predicates.toArray(predicates.toArray(new Predicate[0])));
            //    log.info(predicates.toArray(predicates.toArray(new Predicate[0])).toString());
            }

            if (nOrder != null) {
            	nOrder.stream().forEach(e ->{
            		if(e.getType()==JPAOrderByEnum.ASC)
            			cq.orderBy(cb.asc(root.get(e.getColumnName())));
            		if(e.getType()==JPAOrderByEnum.DESC)
            			cq.orderBy(cb.desc(root.get(e.getColumnName())));     		
                

            		
            	});
             }


            TypedQuery query = entityManger.createQuery(cq);

            if (fromRecord != 0) {
                query.setFirstResult(fromRecord);
            }

            if (maxResult != 0) {
                query.setMaxResults(maxResult);
            }


            result = query.getResultList();

            if (result.size() == 0) {
                String temp = beanClass.toString();
                int x = temp.lastIndexOf(".");

                String errorClass = temp.substring(x + 1, temp.length());
                throw new EmptyResultSetException("error.emptyRS," + errorClass);
            }

            return result;

        } catch (DataAccessException e) {
            String temp = beanClass.toString();
            int x = temp.lastIndexOf(".");
            String errorClass = temp.substring(x + 1, temp.length());
            throw new DataBaseException("error.dataBase.query," + errorClass + "," + e.getMessage());
        } finally {
      //     log.info("finally clause ");
        }
    }


   
    
	@Override 
	public void insertBean(Object newBean)throws DataBaseException
	{
		try 
		{
			
		 
		 
			if(newBean!=null)
			{
				
				
			
				 
 				entityManger.persist(newBean);
				
			}

		}
		catch (DataAccessException e) 
		{						
			throw new DataBaseException("error.dataBase.insert,"+newBean.getClass().getSimpleName()+","+e.getMessage());		
		}
	}
	@Override 
	public void insertBeans(List beans)throws DataBaseException
	{
		
			if(beans!=null)
			{
				beans.stream().forEach(e-> entityManger.persist(e));
			 
			}
		
		
	}
	
 
 
	@Override
	public void deleteBean(Object bean)throws DataBaseException
	{
	 if(bean!=null)
			{
				 
				entityManger.remove(bean);
			 
			}
		 
	 
	}

	@Override
	public void deleteBeans(List beans)throws DataBaseException
	{ 
		
		

		if(beans!=null && beans.size()>0)
		{
			beans.stream().forEach(e-> entityManger.remove(e));
	 
		}
	
		
		
		
		
		
	}	

	@Override
	public Object findBean(Class<?> beanClass, Object identifier) throws DataException,InvalidReferenceException, DataBaseException
	{
		List<Object> result;
 
		try 
		{     
			
			CriteriaBuilder cb = entityManger.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(beanClass);
            Root root = cq.from(beanClass);

      

			cq.where(cb.equal(root.get("id"), identifier));
				Query query=entityManger.createQuery(cq);
			 

			result = query.getResultList();
			
			if(result.size()==0)
			{
				String temp=beanClass.toString();
				int x=temp.lastIndexOf(".");
				
				String errorClass =temp.substring(x+1,temp.length());
				
				throw new InvalidReferenceException("error.invalidRef,"+errorClass);

			}
			
			return result.iterator().next();
			
		}
		catch (DataAccessException e) 
		{
			String temp=beanClass.toString();
			int x = temp.lastIndexOf(".");
			String errorClass =temp.substring(x+1,temp.length()); 
			throw new DataBaseException("error.dataBase.query,"+errorClass+","+e.getMessage());
		}	
 
	}
	@Override
	public Object findBean(Class<?> beanClass, Map propertyMap) throws DataBaseException, EmptyResultSetException
	{
		List<Object> result=null;
	 
		try 
		{
			
			CriteriaBuilder cb = entityManger.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(beanClass);
            List<Predicate> predicates = new ArrayList<>();

            Root root = cq.from(beanClass);
			String key;
			
			for(Iterator itr = propertyMap.keySet().iterator() ; itr.hasNext() ;)
			{
				key = (String)itr.next();
                predicates.add(cb.equal(root.get(key), propertyMap.get(key)));


 			}
			
			if(predicates.size()>0) {
 				cq.where( predicates.toArray(predicates.toArray(new Predicate[0])));

			}
			Query query=entityManger.createQuery(cq);	
			result = query.getResultList();
			
			if(result.size()==0)
			{
				String temp=beanClass.toString();
				int x=temp.lastIndexOf(".");
				
				String errorClass =temp.substring(x+1,temp.length());
				
				throw new EmptyResultSetException("error.emptyRef,"+errorClass);
			}
			
			return result.iterator().next();
		}
		catch (DataAccessException e) 
		{
			String temp=beanClass.toString();
			int x = temp.lastIndexOf(".");
			String errorClass =temp.substring(x+1,temp.length()); 
			throw new DataBaseException("error.dataBase.query,"+errorClass+","+e.getMessage());
		}		
		finally
		{ }
	}
	
	
	@Override
	public Object  aggregate(String tablename,String operation,String columnName,Map <String,Object>parameters) throws DataBaseException, EmptyResultSetException {
		
		
		
		 
		  try { 
			 
		String query="select "+operation+"("+columnName+") from "+tablename+ " where 1=1";
		
	if(parameters!=null)
	     for (Map.Entry entry :	parameters.entrySet())  {

	    	 query+=" and "+String.valueOf( entry.getKey()) +String.valueOf(entry.getValue());
	    }
	     
	        List result=  entityManger.createQuery(query).getResultList();
	        
	        if(result!=null&&result.get(0)!=null&&result.size() == 0) {
				  throw new  EmptyResultSetException("error.emptyRS"); }
			 
			  if(result.size() > 0) 
			  { return (result.get(0)==null)?null:result.get(0) ;}
	      
	        
		  }catch(DataAccessException e) {
			  throw new
			  DataBaseException("error.dataBase.query,AgentFinancialStatus,"+e.getMessage()  );
			  }
			  finally {  
			  }
		return null;
		  
		  
	}
	
	@Override  
	public void PrintReport(Map param, InputStream report) {
		/*
		 * 
		 * 
		 * Session session = (Session) entityManger.getDelegate();
		 * 
		 * session.doWork(new Work() {
		 * 
		 * @Override public void execute(Connection connection) throws SQLException {
		 * JasperReport jr = null; try { jr =
		 * JasperCompileManager.compileReport(report); } catch (JRException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * log.info("paramters=> "+ param.toString());
		 * 
		 * param.put(JRParameter.REPORT_LOCALE, new Locale("ar", "AE", "Arabic"));
		 * 
		 * JasperPrint jp=null; try { jp = JasperFillManager.fillReport(jr, param,
		 * connection);
		 * 
		 * 
		 * JasperViewer.viewReport(jp, false, new Locale("ar", "AE", "Arabic")); } catch
		 * (JRException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
		 * });
		 * 
		 * 
		 * 
		 */}
 
	
	
	
	
	public Season getCurrentSeason() throws DataBaseException, EmptyResultSetException {
		

		
 		  try { 
 		  
		  String query =
		  "from Season where currentSeason = 1";
		  Query queryList = entityManger.createQuery(query);
		
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
	@Override
	public List<Object> findAllBeansWithDepthMapping(Class beanClass, Map propertyMap,List<String> nOrder) 
	throws DataBaseException,EmptyResultSetException
	{
 		try
		{
			String key, value;
			StringBuffer sb = new StringBuffer("");
			
			sb.append("from " + beanClass.getSimpleName() + " as bean");
			
			boolean andFlag=false;
			
			for(Iterator<Object> itr = propertyMap.keySet().iterator() ; itr.hasNext() ;)
			{
				if(andFlag)
					sb.append(" and");
				else
					sb.append(" where");
				key = (String)itr.next();
				value = String.valueOf(propertyMap.get(key));
				sb.append(" bean."+key);
				if(!value.equals("is null") && !value.equals("is not null")&&!value.contains(">")&&!value.contains("<")&& !value.contains("<> "))
					sb.append("=");
				sb.append(" " + value + " ");
				
				andFlag=true;
			}
			
			if(nOrder!=null)
			{
				sb.append(" ORDER BY  ");
 				Iterator itr = nOrder.iterator(); 
				while(itr.hasNext())
				{
 					sb.append(" bean."+itr.next().toString());
					if(itr.hasNext())
						sb.append(" , ");
				}
			}
			System.out.println(sb.toString());
 			List<Object> result = entityManger.createQuery(sb.toString()).getResultList();

			if(result.size()==0)
				throw new EmptyResultSetException("error.emptyRS," + beanClass.getSimpleName());
			
			return result;
		}
		catch(DataAccessException e)
		{
			throw new DataBaseException("error.dataBase.query," + beanClass.getSimpleName() + "," + e.getMessage());
		}
		finally
        {
 
        }
	}

	

	@Override
	public void mergeEntity(Object entity) {
		
		entityManger.merge(entity);
	}
	
	
}
