package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Income;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepo  extends CrudRepository<Income,Integer> {
}
