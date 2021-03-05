package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Fridage;
import com.gomalmarket.shop.core.entities.ShopLoan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncLoanRepo  extends CrudRepository<ShopLoan,Integer> {
}
