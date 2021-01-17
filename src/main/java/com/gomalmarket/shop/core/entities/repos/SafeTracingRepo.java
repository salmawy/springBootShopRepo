package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Outcome;
import com.gomalmarket.shop.core.entities.SafeTracing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SafeTracingRepo    extends CrudRepository<SafeTracing,Integer> {
}
