package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.safe.SeasonSafe;

@Repository
public interface SafeRepo   extends CrudRepository<SeasonSafe,Integer> {
}
