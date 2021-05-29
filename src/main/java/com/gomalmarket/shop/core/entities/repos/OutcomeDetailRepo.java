package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.expanses.OutcomeDetail;

@Repository
public interface OutcomeDetailRepo  extends CrudRepository<OutcomeDetail,Integer> {
}
