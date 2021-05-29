package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.contractor.Contractor;
import com.gomalmarket.shop.core.entities.customers.CustomerType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTypeRepo extends CrudRepository<CustomerType,Integer> {
}
