package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.expanses.IncomeDetail;

@Repository
public interface IncomeDetailRepo  extends CrudRepository<IncomeDetail,Integer> {
}
