package com.gomalmarket.shop.core.service.impl;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.gomalmarket.shop.core.JPAOrderBy;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.basic.BaseEntity;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.sellers.SellerOrderWeight;

//import com.sps.passport.bean.ShipVisitSummeryNationality;

import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.core.service.dao.IBaseDao;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

@SuppressWarnings("unchecked")
@Slf4j
@Service
@Getter
public class BaseService implements IBaseService {

	@Autowired
	ShopAppContext shopAppContext;

	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Autowired
	IBaseDao baseDao;

	@Override
	public List<Object> findAllBeans(Class<?> beanClass, Map params, List <JPAOrderBy> order)
			throws DataBaseException, EmptyResultSetException {
		 
		/*
		 * if (order != null) nOrder.add((JPAOrderBy) order);
		 */

		List<Object> nExpression = new ArrayList<Object>();
		String key;

		return this.getBaseDao().findAllBeans(beanClass, params, order, 0, 0);
	}
	
	
	
	@Override
    public   <T> List<T> gFindAllBeans(Class<?> beanClass, Map params, List <JPAOrderBy> order) throws EmptyResultSetException, DataBaseException {
 
		/*
		 * if (order != null) nOrder.add((JPAOrderBy) order);
		 */

		List<Object> nExpression = new ArrayList<Object>();
		String key;

		return this.getBaseDao().gFindAllBeans(beanClass, params, order, 0, 0);	}
	
	
	
	
	
	
	
	

	@Override
	public List findAllBeans(Class<?> beanClass) throws DataBaseException, EmptyResultSetException {
		return this.getBaseDao().findAllBeans(beanClass, null, null, 0, 0);
	}

	@Override
	public void addBean(Object newBean) throws DataBaseException {

		setChangeInformation(newBean);

		this.getBaseDao().insertBean(newBean);

	}

	@Override
	public void addEditBean(Object newBean) throws DataBaseException {

		setChangeInformation(newBean);

		this.getBaseDao().insertBean(newBean);

	}

	@Override
	public void editBean(Object newBean) throws DataBaseException {

		setChangeInformation(newBean);

		this.getBaseDao().insertBean(newBean);

	}

	@Override
	public void addEditBeans(List Beans) throws DataBaseException {

		Beans.stream().forEach(e -> setChangeInformation(e));
		this.getBaseDao().insertBeans(Beans);

	}

	@Override
	public void deleteBean(Object newBean) throws DataBaseException {

		this.getBaseDao().deleteBean(newBean);

	}

	public void setChangeInformation(Object bean) {
		try {
			if (bean != null) {
				((BaseEntity) bean).setChangerId(shopAppContext.getCurrentUser().getId());
				((BaseEntity) bean).setChangeDate(new Date());
				((BaseEntity) bean).setChanged(1);

			}
		} catch (Exception e) {
		}
	}

	@Override
	public Object findBean(Class<?> beanClass, Map propertyMap) throws DataBaseException, EmptyResultSetException {
		return this.getBaseDao().findBean(beanClass, propertyMap);
	}
	public <T> T gFindBean(Class<T> beanClass, Map propertyMap) throws DataBaseException, EmptyResultSetException {
		return this.getBaseDao().gFindBean(beanClass, propertyMap);
	}
	@Override
	public Object findBean(Class<?> beanClass, int id) throws DataBaseException, InvalidReferenceException {
		return this.getBaseDao().findBean(beanClass, id);
	}

	public Object aggregate(String tablename, String operation, String columnName, Map<String, Object> parameters)
			throws DataBaseException, EmptyResultSetException {

		return getBaseDao().aggregate(tablename, operation, columnName, parameters);

	}

	@Override
	public List<Object> findAllBeansWithDepthMapping(Class<?> beanClass, Map propertyMap)
			throws DataBaseException, EmptyResultSetException {
		// TODO Auto-generated method stub
		return getBaseDao().findAllBeansWithDepthMapping(beanClass, propertyMap);
	}

	
	public <T> List<T> gFindAllBeansWithDepthMapping(Class<T> beanClass, Map  propertyMap) throws DataBaseException, EmptyResultSetException{

		// TODO Auto-generated method stub
		return getBaseDao().gFindAllBeansWithDepthMapping(beanClass, propertyMap);
	
	}
	
	
	
	
	
	
	
	@Override
	public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	@Override
	public Date convertToDateViaInstant(LocalDate dateToConvert) {
		return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public void printReport(Map param, InputStream report) throws DataBaseException, JRException {

		getBaseDao().PrintReport(param, report);

	}
	public Object getBean(Class<?> beanClass, Integer identifier) throws DataBaseException, InvalidReferenceException {
		return this.getBaseDao().findBean(beanClass, identifier);
	}
	
	
	@Override
	public Season getCurrentSeason() throws DataBaseException, EmptyResultSetException {
		return this.getBaseDao().getCurrentSeason();
	}
	
	@Override	
	public void mergeEntity(Object entity) {
		
		
		this.getBaseDao().mergeEntity(entity);
	}

	@Override
	public List findAllBeans(Class<SellerOrderWeight> beanClass, Map<String, Object> ParamtersMap) throws DataBaseException, EmptyResultSetException {
	return getBaseDao().findAllBeans(beanClass, ParamtersMap, null, 0, 0);
		
	}
	@Override
	public List<Object> findAllBeansWithDepthMapping(Class beanClass, Map propertyMap, List<String> nOrder)
			throws DataBaseException, EmptyResultSetException {
		return this.getBaseDao().findAllBeansWithDepthMapping(beanClass, propertyMap, nOrder);
	}

	@Override
	public void saveEntity(CrudRepository repo, Object entity) {
		setChangeInformation(entity);
		repo.save(entity);
		
	}
}
