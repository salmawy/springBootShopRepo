
package com.gomalmarket.shop.core.service;


import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;

import com.gomalmarket.shop.core.JPAOrderBy;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.sellers.SellerOrderWeight;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;

import net.sf.jasperreports.engine.JRException;


public interface IBaseService
{

	List<Object> findAllBeans(Class<?> beanClass, Map params, List <JPAOrderBy> order) throws DataBaseException,
			EmptyResultSetException;

	public List<Object> findAllBeansWithDepthMapping(Class<?> beanClass, Map  propertyMap) throws DataBaseException, EmptyResultSetException;

	List findAllBeans(Class<?> beanClass) throws DataBaseException, EmptyResultSetException;

	void editBean(Object newBean) throws DataBaseException;

	void addEditBean(Object newBean) throws DataBaseException;

	void addBean(Object newBean) throws DataBaseException;

	Object findBean(Class<?> beanClass, Map propertyMap) throws DataBaseException,EmptyResultSetException;

	Object findBean(Class<?> beanClass, int id) throws DataBaseException, InvalidReferenceException;

	void deleteBean(Object newBean) throws DataBaseException;

	Object aggregate(String tablename, String operation, String columnName, Map<String, Object> parameters)
			throws DataBaseException, EmptyResultSetException;

	void addEditBeans(List Beans) throws DataBaseException;

	LocalDate convertToLocalDateViaMilisecond(Date dateToConvert);

	Date convertToDateViaInstant(LocalDate dateToConvert);

	void printReport(Map param, InputStream report) throws DataBaseException, JRException;

	public Object getBean(Class<?> beanClass, Integer identifier) throws DataBaseException, InvalidReferenceException ;
	 
	public Season getCurrentSeason() throws DataBaseException, EmptyResultSetException ;

	void mergeEntity(Object entity);
	void saveEntity(CrudRepository repo ,Object entity);

	List findAllBeans(Class<SellerOrderWeight> beanClass, Map<String, Object> ParamtersMap) throws DataBaseException, EmptyResultSetException;

	List<Object> findAllBeansWithDepthMapping(Class beanClass, Map propertyMap, List<String> nOrder)
			throws DataBaseException, EmptyResultSetException;


}