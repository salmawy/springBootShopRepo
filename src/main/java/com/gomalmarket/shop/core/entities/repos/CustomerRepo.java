package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends CrudRepository<Customer,Integer> {
}
