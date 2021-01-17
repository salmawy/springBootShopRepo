package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.LoanAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanAccountRepo  extends CrudRepository<LoanAccount,Integer> {
}
