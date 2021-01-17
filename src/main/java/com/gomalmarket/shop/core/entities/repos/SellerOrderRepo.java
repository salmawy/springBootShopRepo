package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Season;
import com.gomalmarket.shop.core.entities.SellerOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SellerOrderRepo   extends CrudRepository<SellerOrder,Integer>  {
}
