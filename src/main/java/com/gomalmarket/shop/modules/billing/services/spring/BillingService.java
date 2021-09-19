package com.gomalmarket.shop.modules.billing.services.spring;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gomalmarket.shop.core.Enum.CustomerTypeEnum;
import com.gomalmarket.shop.core.Enum.InvoiceStatusEnum;
import com.gomalmarket.shop.core.Enum.OutcomeTypeEnum;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.customers.CustomerOrder;
import com.gomalmarket.shop.core.entities.expanses.OutcomeType;
import com.gomalmarket.shop.core.entities.repos.CustomerOrderRepo;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.modules.Customer.service.ICustomerService;
import com.gomalmarket.shop.modules.billing.dao.IBillingDao;
import com.gomalmarket.shop.modules.billing.services.IBillingService;
import com.gomalmarket.shop.modules.expanses.services.IExpansesServices;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
@Service
@Getter
public class BillingService implements IBillingService {

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private IExpansesServices expansesService;

	@Autowired
	private IBaseService baseService;

	@Autowired
	private IBillingDao billingDao;

	@Autowired
	private ShopAppContext shopAppContext;

	@Override
	public List getSuggestedOrders(int customerId, int status, int seasonId, int typeId, int fridageId)
			throws DataBaseException, EmptyResultSetException {

		Map<String, Object> map = new HashMap<String, Object>();
		if (typeId != 0)
			map.put("customer.type.id", typeId);
		if (typeId != 0)
			map.put("customer.id", customerId);
		map.put("season.id", seasonId);
		map.put("invoiceStatus", status);

		map.put("fridage.id", fridageId);

		return this.getBaseService().findAllBeansWithDepthMapping(CustomerOrder.class, map);

	}

	@Override
	public List getSuggestedCustomersOrders(int status, int seasonId, int fridageId, int typeId)
			throws EmptyResultSetException, DataBaseException {

		return getBillingDao().getSuggestedCustomersOrders(status, seasonId, fridageId, typeId);

	}

	@Override
	public List getCustomersOrderWeights(int orderId) throws EmptyResultSetException, DataBaseException {

		return this.getBillingDao().getCustomersOrderWeights(orderId);
	}

	@Override
	public List getSuggestedCustomersOrders(int seasonId, int fridageId)
			throws EmptyResultSetException, DataBaseException {
		return getBillingDao().getSuggestedCustomersOrders(seasonId, fridageId);
	}

	@Override
	@Transactional
	public void generateInvoice(CustomerOrder invoice) throws DataBaseException, InvalidReferenceException {

 
		
		CustomerOrderRepo invoiceRepo=this.getShopAppContext().getRepoSupplier().getCustomerOrderRepo();
		CustomerOrder savedInvoice= invoiceRepo.findById(invoice.getId()).get();
		
		savedInvoice.setNetWeight(invoice.getNetWeight());
		savedInvoice.setNetPrice(invoice.getNetPrice());
		savedInvoice.setTips(invoice.getTips());
		savedInvoice.setCommision(invoice.getCommision());
		savedInvoice.setRatio(invoice.getRatio());
		savedInvoice.setNotes(invoice.getNotes());
		savedInvoice.setFinished(invoice.getFinished());
		savedInvoice.setInvoiceStatus(invoice.getInvoiceStatus());
		savedInvoice.setEditeDate(invoice.getEditeDate());
		savedInvoice.setTotalPrice(invoice.getTotalPrice());
		invoiceRepo.save(savedInvoice);
		this.getExpansesService().outcomeTransaction(new Date(), savedInvoice.getTips(), savedInvoice.getNotes(),
				OutcomeTypeEnum.INVOICE_TIPS, savedInvoice.getCustomer().getId(), savedInvoice.getId(),
				shopAppContext.getFridage(), shopAppContext.getSeason());

	}

	@Override
	public void payInvoice(CustomerOrder invoice) throws DataBaseException, InvalidReferenceException {

		if (invoice.getCustomer().getType().getId() == CustomerTypeEnum.purchases ||

				invoice.getCustomer().getType().getId() == CustomerTypeEnum.kareem
				|| invoice.getCustomer().getType().getId() == CustomerTypeEnum.mahmed) {

			this.getExpansesService().outcomeTransaction(invoice.getDueDate(), invoice.getNetPrice(), "",
					OutcomeTypeEnum.ORDER_PAY, invoice.getCustomer().getId(), invoice.getId(),
					shopAppContext.getFridage(), shopAppContext.getSeason());
		}

		this.getBaseService().addEditBean(invoice);

	}

}
