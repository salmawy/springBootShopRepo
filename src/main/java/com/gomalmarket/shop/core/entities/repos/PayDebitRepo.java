package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.shopLoan.PayDebit;

@Repository
public interface PayDebitRepo extends CrudRepository<PayDebit,Integer> {

	
	
	

}