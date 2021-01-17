package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Fridage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridageRepo extends CrudRepository<Fridage,Integer> {
}
