package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.IncomeDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeDetailRepo  extends CrudRepository<IncomeDetail,Integer> {
}
