package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.expanses.Outcome;
import com.gomalmarket.shop.core.entities.safe.SafeTracing;

@Repository
public interface SafeTracingRepo    extends CrudRepository<SafeTracing,Integer> {
}
