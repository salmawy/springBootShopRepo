package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.ContractorAccount;
import com.gomalmarket.shop.core.entities.ContractorAccountDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorAccountDetailRepo extends CrudRepository<ContractorAccountDetail,Integer> {
}
