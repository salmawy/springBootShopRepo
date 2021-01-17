package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Contractor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorRepo extends CrudRepository<Contractor,Integer> {


}
