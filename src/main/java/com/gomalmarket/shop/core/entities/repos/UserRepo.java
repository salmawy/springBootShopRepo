package com.gomalmarket.shop.core.entities.repos;

import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.basic.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo   extends CrudRepository<User,Integer> {
}
