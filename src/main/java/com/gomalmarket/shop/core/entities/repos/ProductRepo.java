package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.basic.Product;
import com.gomalmarket.shop.core.entities.expanses.Outcome;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo  extends CrudRepository<Product,Integer> {
}
