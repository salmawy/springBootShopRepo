package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.OutcomeDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeDetailRepo  extends CrudRepository<OutcomeDetail,Integer> {
}
