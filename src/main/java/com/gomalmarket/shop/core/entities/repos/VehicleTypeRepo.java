package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.customers.VehicleType;

@Repository
public interface VehicleTypeRepo extends CrudRepository<VehicleType,Integer> {
}
