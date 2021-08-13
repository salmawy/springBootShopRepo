package com.gomalmarket.shop.core.entities.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomalmarket.shop.core.entities.shopLoan.LoanCredit;

@Repository
public interface LoanCreditRepo extends CrudRepository<LoanCredit,Integer> {

	
	
	

}