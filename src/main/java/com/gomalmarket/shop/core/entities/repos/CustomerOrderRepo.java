package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Contractor;
import com.gomalmarket.shop.core.entities.CustomerOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepo extends CrudRepository<CustomerOrder,Integer> {
}
