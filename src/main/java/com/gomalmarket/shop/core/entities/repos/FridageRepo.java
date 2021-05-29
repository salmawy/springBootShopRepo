package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.basic.Fridage;

@Repository
public interface FridageRepo extends CrudRepository<Fridage,Integer> {
}
