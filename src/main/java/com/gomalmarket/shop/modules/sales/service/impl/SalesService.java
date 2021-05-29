package com.gomalmarket.shop.modules.sales.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.Enum.IncomeTypeEnum;
import com.gomalmarket.shop.core.Enum.SellerTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.customers.CustomerOrder;
import com.gomalmarket.shop.core.entities.expanses.IncomeDetail;
import com.gomalmarket.shop.core.entities.expanses.Installment;
import com.gomalmarket.shop.core.entities.sellers.Seller;
import com.gomalmarket.shop.core.entities.sellers.SellerLoanBag;
import com.gomalmarket.shop.core.entities.sellers.SellerOrder;
import com.gomalmarket.shop.core.entities.sellers.SellerOrderWeight;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;
import com.gomalmarket.shop.modules.sales.action.SalesAction;
import com.gomalmarket.shop.modules.sales.service.ISalesService;
import com.gomalmarket.shop.modules.sales.service.dao.ISalesDao;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Getter
@Slf4j

public class SalesService implements ISalesService {

	@Autowired
	ISalesDao salesDao;
	
	@Autowired
	IExpansesServices expansesServices;

	@Autowired
	IBaseService baseService;
	private Map<String, Object> entityDictionary;
	
	
	@Autowired
	ShopAppContext shopAppContext;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*
	 * getAllCustomersOrdersTags
	 */
	@SuppressWarnings("unchecked")
	public List getAllCustomersOrdersTags(int seasonId, int fridageId, int productId, int storeId) {

		List result = new ArrayList();
		Map map = new HashMap();
		map.put("product.id", productId);
		map.put("fridage.id", fridageId);
		map.put("store.id", storeId);
		map.put("finished", 0);

		try {
			result = this.getBaseService().findAllBeansWithDepthMapping(CustomerOrder.class, map);
		} catch (DataBaseException | EmptyResultSetException e) {
			// e.printStackTrace();
		}
		return result;

	}

	public Object aggregate(String tablename, String operation, String columnName, Map<String, Object> parameters)
			throws DataBaseException, EmptyResultSetException {

		return this.getBaseService().aggregate(tablename, operation, columnName, parameters);

	}

	public List<String> getSuggestedSellerName(String searchString) {

		return this.getSalesDao().getSuggestedSellerName(searchString);
	}

	public List getSellersOrders(Date orderDate) throws EmptyResultSetException, DataBaseException {

		return this.getSalesDao().getSellersOrders(orderDate);
	}

	public void saveSellerOrder(Seller seller, SellerOrder sellerOrder, double paidAmount) throws Exception {

 		seller = saveSeller(seller);

		switch (seller.getTypeId()) {

		case SellerTypeEnum.cash:
			sellerOrder.setSellerLoanBagId(0);
			sellerOrder.setSeller(seller);

			this.getBaseService().addBean(sellerOrder);
			
			expansesServices.incomeTransaction(sellerOrder.getOrderDate(), sellerOrder.getTotalCost(), "", IncomeTypeEnum.CASH, seller.getId(), sellerOrder.getId(), sellerOrder.getFridage(), sellerOrder.getSeason());
 
			break;
		case SellerTypeEnum.permenant:
			int bagId = saveAndUpdateSellerLoanBag(seller, sellerOrder.getSeason(), sellerOrder.getTotalCost(),
					paidAmount);
			sellerOrder.setSellerLoanBagId(bagId);
			sellerOrder.setSeller(seller);
			this.getBaseService().addBean(sellerOrder);

			if (paidAmount > 0)
				saveSellerInstalment(seller.getId(), sellerOrder.getId(), bagId, sellerOrder.getFridage(),sellerOrder.getSeason(),
						paidAmount, sellerOrder.getOrderDate(), "");
			break;
		}

		List orderdetail = new ArrayList();
		for (Iterator iterator = sellerOrder.getOrderWeights().iterator(); iterator.hasNext();) {
			SellerOrderWeight temp = (SellerOrderWeight) iterator.next();
			temp.setSellerOrder(sellerOrder );
			orderdetail.add(temp);

		}
		// to update seller order detail by new after saving into database and take new
		// its new id from database
		this.getBaseService().addEditBeans(orderdetail);
 		log.info( "tranasction completed succfully");

	}

	@Override
	public void editeSellerOrder(Seller newSeller, SellerOrder newOrder, double paidAmount, SellerOrder oldOrder,
			int seasonId) throws Exception {

		// ===============================================================================================================================================

		if (newSeller.getTypeId() != oldOrder.getSeller().getTypeId()) {

			this.deleteOldSellerOrder(oldOrder);
			this.saveSellerOrder(newSeller, newOrder, paidAmount);

		}
//===============================================================================================================================================
		else if (newSeller.getTypeId() == oldOrder.getSeller().getTypeId()
				&& newSeller.getTypeId() == SellerTypeEnum.cash) {

			editCashSellerOrder(newSeller, newOrder, paidAmount, oldOrder);
		}

		// =========================================================================================================================================
		else if (newSeller.getTypeId() == oldOrder.getSeller().getTypeId()
				&& newSeller.getTypeId() == SellerTypeEnum.permenant) {
			 
			editPermenantSellerOrder(newSeller, newOrder, paidAmount, oldOrder, seasonId);
		}

	}

	public void editCashSellerOrder(Seller seller, SellerOrder newOrder, double paidAmount, SellerOrder oldOrder)
			throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
 		IncomeDetail detail=null;
		 Double oldPaidAmount = 0.0;

		try {
		    map=new HashMap<String, Object>();
			map.put("sellerOrderId", oldOrder);
			  detail=	(IncomeDetail) this.getBaseService().findBean(IncomeDetail.class, map);
			  oldPaidAmount = detail.getAmount();

		}catch (EmptyResultSetException e) {
			// TODO: handle exception
		}
 		if (oldPaidAmount!=paidAmount) {
 			 
  	 			//add or subtract is relative to safe 
  	 			this.getExpansesServices().editIncomeTransaction(newOrder.getOrderDate(), newOrder.getTotalCost(), "", IncomeTypeEnum.CASH, newOrder.getSeller().getId(), newOrder.getId(), newOrder.getFridage(), newOrder.getSeason(), detail.getId());
 				
 		 
 		}
		 
	 
 				newOrder.setId(oldOrder.getId());
	 
		this.getBaseService().addEditBean(newOrder);

 
	    
	}

	public void editPermenantSellerOrder(Seller seller, SellerOrder newOrder, double paidAmount, SellerOrder oldOrder,
			int seasonId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		IncomeDetail oldPaidAmountDetail = null;
		Double oldPaidAmount = 0.0;

		try {
			map = new HashMap<String, Object>();
			map.put("sellerOrderId", oldOrder.getId());
			map.put("typeId", IncomeTypeEnum.INTST_PAY);
			oldPaidAmountDetail = (IncomeDetail) this.getBaseService().findBean(IncomeDetail.class, map);
			oldPaidAmount = oldPaidAmountDetail.getAmount();

		} catch (EmptyResultSetException e) {
			oldPaidAmountDetail = null;
			oldPaidAmount = 0.0;
		}

		boolean sellerChanged = false;
//========================================if seller changed case============================================================================	
		Seller seller_ = oldOrder.getSeller();

		if (!seller.getName().equals(oldOrder.getSeller().getName())) {

			seller_ =  saveSeller(seller);
			sellerChanged = true;
		}

		newOrder.setSeller(null);
		newOrder.setSeller(seller);
		seller=seller_;

//========================================handle loanBag changed case============================================================================	
		int newLoanBagId = 0;

		clearOrderFromSellerLoanBag(oldOrder.getSeller(), oldOrder.getSeason().getId(), oldOrder.getTotalCost(),
				oldPaidAmount);
		newLoanBagId = saveAndUpdateSellerLoanBag(seller, newOrder.getSeason(), newOrder.getTotalCost(),
				paidAmount);
		newOrder.setSellerLoanBagId(newLoanBagId);

//=====================================get installment and update it ============================================================================

		if (oldPaidAmountDetail != null && paidAmount > 0.0) {
			map = new HashMap<String, Object>();
			map.put("sellerOrderId", oldOrder.getId());
			Installment installment = (Installment) this.getBaseService().findBean(Installment.class, map);
			installment.setSellerLoanBagId(newLoanBagId);
			installment.setAmount(paidAmount);
			this.getBaseService().addEditBean(installment);
		} else if (paidAmount == 0.0 && oldPaidAmountDetail != null) {
			map = new HashMap<String, Object>();
			map.put("sellerOrderId", oldOrder.getId());
			Installment installment = (Installment) this.getBaseService().findBean(Installment.class, map);

			this.getBaseService().deleteBean(installment);

		}

		// ===============================change income detail
		// =========================================================================================

		if (oldPaidAmountDetail != null && paidAmount == 0.0)

		{
			getExpansesServices().deleteIncomeDetailTransaction(oldPaidAmountDetail);
			/*
			 * this.getBaseService().deleteBean(oldPaidAmountDetail);
			 * recalculateSafeBalance(seasonId);
			 */
		} else if (oldPaidAmountDetail != null && oldPaidAmountDetail.getAmount() != paidAmount
				&& paidAmount > 0.0) {

			if (sellerChanged) {
				oldPaidAmountDetail.setSellerId(seller.getId());
				oldPaidAmountDetail.setSellerOrderId(newOrder.getId());
			}
		 
 	 			this.getExpansesServices().editIncomeTransaction(newOrder.getOrderDate(), newOrder.getTotalCost(), "", IncomeTypeEnum.INTST_PAY, newOrder.getSeller().getId(), newOrder.getId(), newOrder.getFridage(), newOrder.getSeason(), oldPaidAmountDetail.getId());

		}

		else if (oldPaidAmountDetail != null && oldPaidAmountDetail.getAmount() == paidAmount && paidAmount > 0.0
				&& sellerChanged) {

			oldPaidAmountDetail.setSellerId(seller.getId());
			oldPaidAmountDetail.setSellerOrderId(newOrder.getId());
			this.getBaseService().addEditBean(oldPaidAmountDetail);

		}

		else if (oldPaidAmountDetail == null && paidAmount > 0.0) {

			saveSellerInstalment(seller.getId(), oldOrder.getId(), newLoanBagId, oldOrder.getFridage(),newOrder.getSeason(),
					paidAmount, oldOrder.getOrderDate(), "");

		}
		// ============================================ detail ================================================================
		List<SellerOrderWeight> orderdetail = new ArrayList<SellerOrderWeight>();
		for (Iterator iterator = newOrder.getOrderWeights().iterator(); iterator.hasNext();) {
			SellerOrderWeight temp = (SellerOrderWeight) iterator.next();
			temp.setSellerOrder(newOrder);
			orderdetail.add(temp);

		}
		// to update seller order detail by new after saving into database and take new
		// its new id from database
		this.getBaseService().addEditBeans(orderdetail);

		this.getBaseService().addEditBean(newOrder);

 
	}

 
 
	public int saveAndUpdateSellerLoanBag(Seller seller, Season season, double orderCost, double paidAmount)
			throws DataBaseException {
		double currentloan = 0;
		SellerLoanBag bag = findSellerLoanBag(seller, season.getId());

		bag.setDueLoan(bag.getDueLoan() + orderCost);
		;
		bag.setPaidAmount(bag.getPaidAmount() + paidAmount);
		currentloan = (bag.getPriorLoan() + bag.getDueLoan()) - paidAmount;
		bag.setCurrentLoan(currentloan);

		this.getBaseService().addEditBean(bag);
//---------------------------------------------------------------------------
		String key = hashDisctionaryEntityKey(SellerLoanBag.class, bag.getId());
		entityDictionary.put(key, bag);
//---------------------------------------------------------------------------

		return bag.getId();
	}

	public int clearOrderFromSellerLoanBag(Seller seller, int seasonId, double orderCost, double paidAmount)
			throws DataBaseException {
		double currentloan = 0;
		SellerLoanBag bag = findSellerLoanBag(seller, seasonId);

		bag.setDueLoan(bag.getDueLoan() - orderCost);
		;
		bag.setPaidAmount(bag.getPaidAmount() - paidAmount);
		currentloan = (bag.getPriorLoan() + bag.getDueLoan()) - paidAmount;
		bag.setCurrentLoan(currentloan);

		this.getBaseService().addEditBean(bag);
		// ---------------------------------------------------------------------------
		String key = hashDisctionaryEntityKey(SellerLoanBag.class, bag.getId());
		entityDictionary.put(key, bag);
		// ---------------------------------------------------------------------------

		return bag.getId();

	}

	public Seller saveSeller(Seller seller) throws DataBaseException, InvalidReferenceException {

		if (seller.getTypeId() == SellerTypeEnum.permenant) {
			try {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("name", seller.getName());
				seller = (Seller) this.getBaseService().findAllBeans(Seller.class, m, null).get(0);

				return seller;
			} catch (DataBaseException | EmptyResultSetException e) {
				this.getBaseService().addBean(seller);

			}
		}

		else if (seller.getTypeId() == SellerTypeEnum.cash) {

			seller = (Seller) this.getBaseService().findBean(Seller.class, SalesAction.CashId);

		}

		return seller;

	}

 
 
	public SellerLoanBag findSellerLoanBag(Seller seller, int seasonId) throws DataBaseException {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sellerId", seller.getId());
		m.put("seasonId", seasonId);

		try {
			SellerLoanBag bag = (SellerLoanBag) this.getBaseService().findAllBeans(SellerLoanBag.class, m, null).get(0);
			String key = hashDisctionaryEntityKey(SellerLoanBag.class, bag.getId());
			bag = (entityDictionary.get(key) != null) ? (SellerLoanBag) entityDictionary.get(key) : bag;
			return bag;

		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		SellerLoanBag bag = new SellerLoanBag();
		bag.setPriorLoan(0.0);
		bag.setSeller(seller);
		bag.setSeason (shopAppContext.getSeason());

		this.baseService.addBean(bag);
		String key = hashDisctionaryEntityKey(SellerLoanBag.class, bag.getId());
		entityDictionary.put(key, bag);

		return bag;

	}

	//@Override
	public void saveSellerInstalment(int sellerId, int sellerOrderId, int sellerLoanBagId, Fridage fridage,Season season, double amount,
			Date date, String notes) throws DataBaseException, InvalidReferenceException {

		Installment installment = new Installment();
		installment.setInstalmentDate(date);
		installment.setAmount(amount);
		installment.setSellerLoanBagId(sellerLoanBagId);
		installment.setNotes(notes);
		if (sellerOrderId != 0) {
			installment.setSellerOrderId(sellerOrderId);

		}
		this.getBaseService().addBean(installment);
		
		expansesServices.incomeTransaction(date, amount, notes, IncomeTypeEnum.INTST_PAY, sellerId, sellerOrderId,installment.getId(), fridage, season);

 
	}

	private void deleteOldSellerOrder(SellerOrder order) throws DataBaseException {
		Seller seller = order.getSeller();
		switch (seller.getTypeId()) {

		case SellerTypeEnum.cash:

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("sellerOrderId", order.getId());
			try {
				IncomeDetail incomeDetail = (IncomeDetail) this.getBaseService().findBean(IncomeDetail.class, map);
				
				getExpansesServices().deleteIncomeDetailTransaction(incomeDetail);
			
				
				/*
				 * this.getBaseService().deleteBean(incomeDetail);
				 * recalculateSafeBalance(order.getSeason().getId());
				 */

			}  catch (EmptyResultSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.getBaseService().deleteBean(order);

			break;
		case SellerTypeEnum.permenant:
			map = new HashMap<String, Object>();

			map.put("sellerOrderId", order.getId());
			try {
				IncomeDetail incomeDetail = (IncomeDetail) this.getBaseService().findBean(IncomeDetail.class, map);
				getExpansesServices().deleteIncomeDetailTransaction(incomeDetail);

				/*
				 * this.getBaseService().deleteBean(incomeDetail);
				 * recalculateSafeBalance(order.getSeason().getId());
				 */

			} catch (EmptyResultSetException e) {
				// TODO: handle exception
			}

			// =========================delete installment============================
			try {
				Installment installment = (Installment) this.getBaseService().findBean(Installment.class,
						map);

				this.getBaseService().deleteBean(installment);

			} catch (EmptyResultSetException e) {
				// TODO: handle exception
			}

			// ================================recalculate loan bag after deleting order======================
			Seller seller_ = order.getSeller();
			int seasonId = order.getSeason().getId();
			this.getBaseService().deleteBean(order);
			recalculeSellerLoanBag(seasonId, seller_);

			break;
		}

	 
		log.info( "tranasction completed succfully");

	}

	public void recalculeSellerLoanBag(int seasonId, Seller seller) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seasonId=", seasonId);
		map.put("sellerId=", seller.getId());

		Double ordersCost = 0.0;
		Double totalPaidAmount = 0.0;

		try {

			SellerLoanBag bag = findSellerLoanBag(seller, seasonId);
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("sellerLoanBagId=", bag.getId());

			ordersCost = (Double) aggregate("SellerOrder", "sum", "totalCost", map);
			ordersCost = (ordersCost == null) ? 0.0 : ordersCost;

			totalPaidAmount = (Double) aggregate("Installment", "sum", "amount", map2);
			totalPaidAmount = (totalPaidAmount == null) ? 0.0 : totalPaidAmount;

			double temp = (bag.getPriorLoan() + ordersCost) - totalPaidAmount;
			bag.setPaidAmount(totalPaidAmount);
			bag.setDueLoan(ordersCost);
			bag.setCurrentLoan(temp);

			this.getBaseService().addEditBean(bag);

		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public double getSellerLoan(int sellerId, int seasonId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sellerId", sellerId);
		map.put("seasonId", seasonId);

		try {
			SellerLoanBag bag = (SellerLoanBag) this.getBaseService().findBean(SellerLoanBag.class, map);
			return bag.getCurrentLoan();

		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0.0;
	}

	public double getSellerOrdersTotalPrice(int sellerId, int seasonId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sellerId", sellerId);
		map.put("seasonId", seasonId);

		try {
			return (double) this.getBaseService().aggregate("SellerOrder", "sum", "totalCost", map);
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0.0;

	}

	public List getSellersDebts(int seasonId, int active) throws EmptyResultSetException, DataBaseException {
		return this.salesDao.getSellersDebts(seasonId, active);

	}

 

	public double getSeasonStartTotalSellersLoan(int seasonId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("season.id=", seasonId);

		try {
			double value = (double) this.aggregate("SellerLoanBag", "sum", "priorLoan", map);
			return value;
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0.0;

	}

	public double getSeasoncCurrentotalSellersLoan(int seasonId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("season.id=", seasonId);

		try {
			double value = (double) this.aggregate("SellerLoanBag", "sum", "currentLoan", map);
			return value;
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0.0;

	}

	@Override
	public SellerLoanBag getSellerLoanBag(int sellerId, int seasonId) throws DataBaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sellerId", sellerId);
		map.put("seasonId", seasonId);
		SellerLoanBag loan = null;
		try {
			loan = (SellerLoanBag) this.getBaseService().findBean(SellerLoanBag.class, map);
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return loan;

	}

 

	private String hashDisctionaryEntityKey(Class<?> beanClass, Integer identifier) {

		String key = "";

		key = beanClass.getName() + identifier;

		return key;

	}

	@Override
	public List getSellersLoanSummary(Date fromDate, Date toDate, int seasonId)
			throws EmptyResultSetException, DataBaseException {
		return this.getSalesDao().getSellersLoanSummary(fromDate, toDate, seasonId);
	}

	 

 
	
	@Override
	public List getSellerOrderWeights(int  sellerOrderId) throws DataBaseException, EmptyResultSetException {
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("sellerOrderId", sellerOrderId);
		
	List weights=	this.baseService.findAllBeans(SellerOrderWeight.class, map);
		
		
		return 	weights;
		
		
		
	}

}
