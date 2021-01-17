package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.ContractorAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorAccountRepo extends CrudRepository<ContractorAccount,Integer> {

}
