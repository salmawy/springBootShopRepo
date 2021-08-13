package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.shopLoan.PayCredit;

@Repository
public interface PayCreditRepo extends CrudRepository<PayCredit,Integer> {

	
	
	

}