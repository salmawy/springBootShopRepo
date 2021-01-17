package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Loaner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanerRepo extends CrudRepository<Loaner,Integer> {
}
