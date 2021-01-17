package com.gomalmarket.shop.core.service.dao;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.DataException;

import com.gomalmarket.shop.core.JPAOrderBy;
import com.gomalmarket.shop.core.entities.Season;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;

public interface IBaseDao {
    List<Object> findAllBeansWithDepthMapping(Class<?> beanClass, Map<Object, Object> propertyMap)
                throws DataBaseException, EmptyResultSetException;

    public List<Object> findAllBeans(Class<?> beanClass, Map<String, Object> params , List<JPAOrderBy> nOrder, int fromRecord, int maxResult) throws DataBaseException, EmptyResultSetException ;

	void insertBean(Object newBean) throws DataBaseException;

	void insertBeans(List beans) throws DataBaseException;

	void deleteBean(Object bean) throws DataBaseException;

	void deleteBeans(List beans) throws DataBaseException;

	Object findBean(Class<?> beanClass, Object identifier)
			throws DataException, InvalidReferenceException, DataBaseException;

	Object aggregate(String tablename, String operation, String columnName, Map<String, Object> parameters)
			throws DataBaseException, EmptyResultSetException;

 
	void PrintReport(Map param, InputStream report);
	
	public Season getCurrentSeason() throws DataBaseException, EmptyResultSetException ;

	Object findBean(Class<?> beanClass, Map propertyMap) throws DataBaseException, EmptyResultSetException;

	void mergeEntity(Object entity);
 
    }
