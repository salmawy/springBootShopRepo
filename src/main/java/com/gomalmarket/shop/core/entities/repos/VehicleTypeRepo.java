package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.VehicleType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepo extends CrudRepository<VehicleType,Integer> {
}
