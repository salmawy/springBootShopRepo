package com.gomalmarket.shop.modules.billing.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

 import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.billing.dao.IBillingDao;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository
@Slf4j

public class BillingDao implements IBillingDao {

	@PersistenceContext
	EntityManager entityManger;

	@Override
	public List getSuggestedCustomersOrders(int seasonId, int fridageId)
			throws EmptyResultSetException, DataBaseException {

		try {

			String query = " select distinct co.customer.id,co.customer.name,co.customer.typeId,co.invoiceStatus from "
					+ "	 CustomerOrder co " + " where co.fridageId=  " + fridageId + "	and co.periodId=-1 "
					+ " and co.seasonId=  " + seasonId;

			Query queryList = entityManger.createQuery(query);

			List<Object> result = queryList.getResultList();

			if (result.size() == 0) {
				throw new EmptyResultSetException("error.emptyRS");
			}

			if (result.size() > 0) {
				return result;
			}
		} catch (DataAccessException e) {
			throw new DataBaseException("error.dataBase.query,AgentFinancialStatus," + e.getMessage());
		} finally {
		}

		return null;
	}

	@Override
	public List getSuggestedCustomersOrders(int status, int seasonId, int fridageId, int typeId)
			throws EmptyResultSetException, DataBaseException {

		try {

			String query = "" + "select distinct customer from " + "	 CustomerOrder co " + " where co.fridageId=  "
					+ fridageId + "	and co.periodId=-1 " + " and co.seasonId=  " + seasonId + " and co.invoiceStatus=  "
					+ status;

			if (typeId != 0) {
				query += " and co.customer.typeId =" + typeId;

			}
			Query queryList = entityManger.createQuery(query);

			List<Object> result = queryList.getResultList();

			if (result.size() == 0) {
				throw new EmptyResultSetException("error.emptyRS");
			}

			if (result.size() > 0) {
				return result;
			}
		} catch (DataAccessException e) {
			throw new DataBaseException("error.dataBase.query,AgentFinancialStatus," + e.getMessage());
		} finally {
		}

		return null;
	}

	@Override
	public List getCustomersOrderWeights(int orderId) throws EmptyResultSetException, DataBaseException {

		try {

			String query = "select " + " sum(amount)," + "	unitePrice," + " sum(netQuantity), "
					+ " sum(packageNumber) " + " from  SellerOrderWeight" + " where  customerOrderId=" + orderId
					+ " group by unitePrice ";

			Query queryList = entityManger.createQuery(query);

			List<Object> result = queryList.getResultList();

			if (result.size() == 0) {
				throw new EmptyResultSetException("error.emptyRS");
			}

			if (result.size() > 0) {
				return result;
			}
		} catch (DataAccessException e) {
			throw new DataBaseException("error.dataBase.query,AgentFinancialStatus," + e.getMessage());
		} finally {
		}

		return null;
	}

}
