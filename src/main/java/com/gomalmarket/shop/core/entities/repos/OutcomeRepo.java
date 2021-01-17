package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.LoanAccount;
import com.gomalmarket.shop.core.entities.Outcome;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeRepo  extends CrudRepository<Outcome,Integer> {
}
