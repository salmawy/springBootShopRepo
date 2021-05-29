package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.basic.Season;

@Repository
public interface SeasonRepo  extends CrudRepository<Season,Integer> {
}
