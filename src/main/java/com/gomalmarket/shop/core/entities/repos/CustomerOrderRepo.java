package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.contractor.Contractor;
import com.gomalmarket.shop.core.entities.customers.CustomerOrder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepo extends CrudRepository<CustomerOrder,Integer> {
}
