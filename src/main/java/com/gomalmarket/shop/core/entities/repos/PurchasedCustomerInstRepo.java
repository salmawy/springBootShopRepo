package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.customers.PurchasedCustomerInst;
import com.gomalmarket.shop.core.entities.expanses.Outcome;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedCustomerInstRepo  extends CrudRepository<PurchasedCustomerInst,Integer> {
}
