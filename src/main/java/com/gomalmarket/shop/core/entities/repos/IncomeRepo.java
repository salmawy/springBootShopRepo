package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.expanses.Income;

@Repository
public interface IncomeRepo  extends CrudRepository<Income,Integer> {
}
