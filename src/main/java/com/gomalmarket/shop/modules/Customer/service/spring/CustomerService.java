package com.gomalmarket.shop.modules.Customer.service.spring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.Enum.CustomerTypeEnum;
import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.Enum.SafeTransactionTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.customers.Customer;
import com.gomalmarket.shop.core.entities.customers.CustomerOrder;
import com.gomalmarket.shop.core.entities.customers.PurchasedCustomerInst;
import com.gomalmarket.shop.core.entities.expanses.OutcomeDetail;
import com.gomalmarket.shop.core.entities.repos.RepoSupplier;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.Customer.dao.ICustomerDao;
import com.gomalmarket.shop.modules.Customer.service.ICustomerService;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;
import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;



@Service
@Transactional
@Setter
@Getter
@Slf4j
public class CustomerService implements ICustomerService {
	
	@Autowired
	ICustomerDao customerDao;
	@Autowired
	IExpansesServices expansesServices;
	
	@Autowired
 	IBaseService baseService;
 
	@Autowired
	ShopAppContext shopAppContext;
	
	@Autowired
	RepoSupplier repoSupplier;

	public List getCustomerOrders(Date orderDate) throws EmptyResultSetException, DataBaseException {		
		return this.customerDao.getCustomerOrders(orderDate);
	}
	public List getCustomerInvoices(Season season,int customerId,Fridage fridage) throws EmptyResultSetException, DataBaseException 
		{
		return this.getCustomerDao().getCustomerOrders(customerId, season, fridage, 1);	
		}
		
		
	public List getCustomerOrders(Season season,int customerId,Fridage fridage) throws EmptyResultSetException, DataBaseException 
	{
	return this.getCustomerDao().getCustomerOrders(customerId, season,  fridage, 0);	
	}
	
	public List getSeasonCustomers(Season season,Fridage fridage,int typeId) throws EmptyResultSetException, DataBaseException {
	return this.getCustomerDao().getSeasonCustomers( season , fridage, typeId);
	
	}



	public Season getCurrentSeason() throws DataBaseException, EmptyResultSetException {
		return this.getCustomerDao().getCurrentSeason();
	}
	public List getCustomersSummaryTransactions(Season season,Fridage fridage,int customerId) throws EmptyResultSetException, DataBaseException {
	
	return this.getCustomerDao().getCustomersSummaryTransactions(season,  fridage, customerId);
	}


	@Override
	public List getPurchasedCustomerData(Season season, Fridage fridage)
			throws EmptyResultSetException, DataBaseException {
		
		Map <String,Object>m=new HashMap<String,Object>();
		m.put("customer.typeId", CustomerTypeEnum.purchases);
		m.put("seasonId", season);
		m.put("periodId", -1);
		List data=new ArrayList(); 
	
		try {
			List<Object>result= this.getSeasonCustomers(season, null, CustomerTypeEnum.purchases);
			for (Object it : result) {
				Customer customer=(Customer) it;
				double cost=this.getCustomerDao().getPurchasedCustomerTotalDue(season, customer.getId());
				double paidAmount=this.getCustomerDao().getPurchasedCustomerTotalInst(season, customer.getId());
				double dueAmount=cost-paidAmount;
				
				List temp= new ArrayList<>(Arrays.asList( 
						customer.getId()
						, customer.getName()
						,cost
						,paidAmount
						,dueAmount
						));
				
				data.add(temp);
				
			}
			
			
		
			
			
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return data;
	}


	 public List<String> getSuggestedCustomerName(String searchString,int customerTypeId) {
	 
	 return this.getCustomerDao().getSuggestedCustomerName(searchString,customerTypeId);
	 }
	 
	 @Transactional(propagation = Propagation.NESTED)
	 public void saveCustomerOrder(CustomerOrder customerOrder) throws DataBaseException, InvalidReferenceException {
		 
		 
			SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");

			
		//===================save customer into Database ========================================================	 
		 Customer customer=saveCustomer(customerOrder.getCustomer());
			
		 
//========================save customer Order into Database ========================================================	
		 customerOrder.setCustomer(customer);
 		 String orderTag=(customerOrder.getCustomer().getName()+"_"+sdf.format(customerOrder.getOrderDate())+"_"+customerOrder.getOrderTag()+"_" +customerOrder.getId());
		customerOrder.setOrderTag(orderTag);
  		this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getCustomerOrderRepo(), customerOrder);

//=============================== save outcome transactions=====================================
 		
 
 		this.getExpansesServices().outcomeTransaction(customerOrder.getOrderDate(), customerOrder.getTips(), customerOrder.getNotes(), 
				OutcomeTypeEnum.TIPS, customerOrder.getCustomer().getId(), customerOrder.getId(), shopAppContext.getFridage(), shopAppContext.getSeason());
		
		this.getExpansesServices().outcomeTransaction(customerOrder.getOrderDate(), customerOrder.getNolun(), customerOrder.getNotes(), 
				OutcomeTypeEnum.NOLOUN, customerOrder.getCustomer().getId(), customerOrder.getId(), shopAppContext.getFridage(), shopAppContext.getSeason());
	 
 
		
 


		
			}

	 
	 public Customer  saveCustomer(Customer customer) throws DataBaseException {
			

			try {
				Map <String,Object>m=new HashMap<String,Object>();
				m.put("name", customer.getName());
				customer=(Customer) this.getBaseService().findAllBeans(Customer.class, m, null).get(0);
				
				
				return customer;
			} catch (DataBaseException | EmptyResultSetException e) {
				
 		  		this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getCustomerRepo(), customer);


				
			}
			
			return customer;
			
		}


	 
	 
	 
	 
		
 
 
 





public void editCustomerOrder(CustomerOrder newOrder,CustomerOrder oldOrder) throws DataBaseException, InvalidReferenceException {
	Map<String,Object> map=new HashMap<String, Object>();
	
	
 
	
	Customer customer=oldOrder.getCustomer();
	
	if(!newOrder.getCustomer().getName().equals(oldOrder.getCustomer().getName()) ) {
		
		customer= saveCustomer(newOrder.getCustomer()) ;
		newOrder.setCustomer(customer);
	 }
	
	

	
	//newOrder.setCustomer(null);

	//====================================================================================================================
	if(!newOrder.getNolun().equals(oldOrder.getNolun() )) {
		map=new HashMap<String, Object>();
		map.put("orderId",oldOrder.getId() );
		map.put("typeId",OutcomeTypeEnum.NOLOUN.getId() );

		try {
			
			//change outcome detail 
			OutcomeDetail nolune=	 (OutcomeDetail) this.getBaseService().findAllBeans(OutcomeDetail.class, map, null).get(0);
 			
	        this.getExpansesServices().editOutcomeTransaction(nolune.getTransactionDate(), newOrder.getNolun(), "", OutcomeTypeEnum.NOLOUN, newOrder.getCustomer().getId(), newOrder.getId(), newOrder.getFridage(), newOrder.getSeason(), nolune.getId());

 		 
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	
	if(!newOrder.getTips().equals(oldOrder.getTips())) {
		
		map=new HashMap<String, Object>();
		map.put("orderId",oldOrder.getId() );
		map.put("type.id",OutcomeTypeEnum.TIPS.getId() );

		try {
				//change outcome detail 
			OutcomeDetail tips=	 (OutcomeDetail) this.getBaseService().findAllBeansWithDepthMapping(OutcomeDetail.class, map).get(0);
   	        this.getExpansesServices().editOutcomeTransaction(tips.getTransactionDate(), newOrder.getTips(), "", OutcomeTypeEnum.TIPS, newOrder.getCustomer().getId(), newOrder.getId(), newOrder.getFridage(), newOrder.getSeason(), tips.getId());

		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
 
 	 
 		this.getBaseService().saveEntity(this.getShopAppContext().getRepoSupplier().getCustomerOrderRepo(), newOrder);

 
    
}


private void deleteOldCustomerOrder(CustomerOrder order) throws DataBaseException {

	
	Map<String, Object> map=new HashMap<String, Object>();

				map.put("orderId", order.getId());
				map.put("typeId", OutcomeTypeEnum.NOLOUN);


				try {
				this.getBaseService().findAllBeans(OutcomeDetail.class, map,null).get(0);

					OutcomeDetail nolun= (OutcomeDetail)this.getBaseService().findAllBeans(OutcomeDetail.class, map,null).get(0);
					getExpansesServices().deleteOutcomeDetailTransaction(nolun);
					
					map=new HashMap<String, Object>();
					map.put("orderId", order.getId());
					map.put("typeId", OutcomeTypeEnum.TIPS);
					
					OutcomeDetail tips= (OutcomeDetail)this.getBaseService().findAllBeans(OutcomeDetail.class, map,null).get(0);
					this.getBaseService().deleteBean(tips);

				
		 //recalculateSafeBalance(order.getSeason() );
				
				}catch (EmptyResultSetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
	
	
	
}




	/*
	 * 
	 * public void confirmPurchasedOrdersPrices(List editedRecords) throws
	 * DataBaseException {
	 * 
	 * 
	 * 
	 * 
	 * System.out.println("confirm prices "); List <Object>beans=new
	 * ArrayList<Object>();
	 * //===========================================================================
	 * ===================================
	 * 
	 * for (Iterator iterator = editedRecords.iterator(); iterator.hasNext();) {
	 * PurchasedOrdersViewBean purchasedOrdersViewBean = (PurchasedOrdersViewBean)
	 * iterator.next(); int id =purchasedOrdersViewBean.getId(); try { CustomerOrder
	 * bean=(CustomerOrder) this.getBaseService().getBean(CustomerOrder.class, id);
	 * bean.setUnitePrice(purchasedOrdersViewBean.getUnitePrice());
	 * bean.setPeriodId(-1);
	 * bean.setBuyPrice(purchasedOrdersViewBean.getBuyPrice()); beans.add(bean);
	 * 
	 * } catch ( InvalidReferenceException e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); }
	 * 
	 * }
	 * //===========================================================================
	 * ===================================
	 * this.getBaseService().addEditBeans(beans);
	 * 
	 * //===========================================================================
	 * ===================================
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */
//@Override
public void payPurchasedOrder(Customer customer,double amount,Date date,String notes,Season season,Fridage fridage) throws DataBaseException, InvalidReferenceException {
	
	PurchasedCustomerInst inst=new PurchasedCustomerInst();
	inst.setCustomer(customer);
	inst.setAmount(amount);
	inst.setInstalmentDate(date);
	inst.setNotes(notes);
	inst.setSeason(season);
	
	
	
	
	
	this.getBaseService().addBean(inst);
	
 	
	
	
	this.getExpansesServices().outcomeTransaction(date, amount, notes, OutcomeTypeEnum.PURCHASES_WITHDRAWALS, customer.getId(), inst.getId(),  fridage, season);

 
	
	
}




//@Override
public void editPurchasedOrder(int installmentId, Customer customer ,double amount,Date date,String notes,Season season,Fridage fridage) throws DataBaseException, InvalidReferenceException {
	
	PurchasedCustomerInst inst=(PurchasedCustomerInst) this.getBaseService().findBean(PurchasedCustomerInst.class, installmentId);
	double oldAmount=inst.getAmount();
	int oldCustomerId=inst.getCustomer().getId();
	inst.setCustomer(customer);
	inst.setAmount(amount);
	inst.setInstalmentDate(date);
	inst.setNotes(notes);
	inst.setSeason(season);
	
	
	Map <String,Object>map=new HashMap<String,Object>();
	map.put("typeId", OutcomeTypeEnum.PURCHASES_WITHDRAWALS);
	map.put("fridageId", fridage);
	map.put("orderId", installmentId);
	map.put("customerId", oldCustomerId);
	OutcomeDetail outcomeDetail=null;
	try {
		outcomeDetail = (OutcomeDetail) this.getBaseService().findBean(OutcomeDetail.class, map);
	} catch (EmptyResultSetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	this.getBaseService().addEditBean(inst);
	if(oldAmount!=inst.getAmount()) {
		
         this.getExpansesServices().editOutcomeTransaction(date, amount, notes, OutcomeTypeEnum.PURCHASES_WITHDRAWALS,customer.getId() , 0, fridage, season, outcomeDetail.getId());
 	
	
	}
	
	
	  if (oldCustomerId!=inst.getCustomer().getId()) {

		outcomeDetail.setCustomerId(inst.getCustomer().getId());
		this.getBaseService().addEditBean(outcomeDetail);
	}


 
	
	
}
 
 








}













