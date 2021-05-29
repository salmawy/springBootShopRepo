package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.sellers.Seller;

@Repository
public interface SellerRepo  extends CrudRepository<Seller,Integer> {
}
