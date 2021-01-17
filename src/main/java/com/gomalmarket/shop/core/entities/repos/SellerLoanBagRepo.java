package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Season;
import com.gomalmarket.shop.core.entities.SellerLoanBag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerLoanBagRepo  extends CrudRepository<SellerLoanBag,Integer> {
}
