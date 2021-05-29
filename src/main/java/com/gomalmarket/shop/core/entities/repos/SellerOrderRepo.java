package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.sellers.SellerOrder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SellerOrderRepo   extends CrudRepository<SellerOrder,Integer>  {
}
