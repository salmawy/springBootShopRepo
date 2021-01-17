package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.LoanPaying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPayingRepo  extends CrudRepository<LoanPaying,Integer> {
}
