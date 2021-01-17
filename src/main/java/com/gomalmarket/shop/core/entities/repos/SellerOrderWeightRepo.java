package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Season;
import com.gomalmarket.shop.core.entities.SellerOrderWeight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerOrderWeightRepo  extends CrudRepository<SellerOrderWeight,Integer> {
}
