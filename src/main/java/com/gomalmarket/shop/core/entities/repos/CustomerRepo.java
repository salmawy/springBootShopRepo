package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.customers.Customer;

@Repository
public interface CustomerRepo extends CrudRepository<Customer,Integer> {
}
