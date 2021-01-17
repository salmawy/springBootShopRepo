package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.SafeOfDay;

@Repository
public interface SafeOfDayRepo   extends CrudRepository<SafeOfDay,Integer> {
}
