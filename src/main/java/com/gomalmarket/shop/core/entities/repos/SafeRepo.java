package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.Safe;

@Repository
public interface SafeRepo   extends CrudRepository<Safe,Integer> {
}
