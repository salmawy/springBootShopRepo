package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.Fridage;
import com.gomalmarket.shop.core.entities.Installment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallmentRepo  extends CrudRepository<Installment,Integer> {
}
