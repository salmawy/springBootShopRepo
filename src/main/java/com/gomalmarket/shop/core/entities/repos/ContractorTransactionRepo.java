package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.contractor.ContractorTransaction;
@Repository
public interface ContractorTransactionRepo extends CrudRepository<ContractorTransaction,Integer> {
}
