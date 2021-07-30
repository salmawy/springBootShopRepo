package com.gomalmarket.shop.core.entities.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter

public class RepoSupplier {
	
	@Autowired
	ContractorTransactionRepo contractorTransactionRepo; 
 
	@Autowired
	ContractorRepo contractorRepo;
	@Autowired
	CustomerOrderRepo customerOrderRepo;
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	CustomerTypeRepo customerTypeRepo;
	@Autowired
	 FridageRepo fridageRepo;
	
	//@Autowired
	//IncLoanRepo incLoanRepo;
	
	@Autowired
	IncomeDetailRepo incomeDetailRepo;
	@Autowired
	IncomeRepo incomeRepo;
	@Autowired
	IncomeTypeRepo incomeTypeRepo;
	@Autowired
	InstallmentRepo installmentRepo;
	//@Autowired
	//LoanAccountRepo loanAccountRepo;
	@Autowired
	LoanerRepo loanerRepo;
	//@Autowired
	//LoanPayingRepo loanPayingRepo;
	
	@Autowired
	OutcomeDetailRepo outcomeDetailRepo;
	
	//@Autowired
	//OutcomeRepo outcomeRepo;
	
	
	//@Autowired 
	//OutcomeTypeRepo outcomeTypeRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired
	PurchasedCustomerInstRepo customerInstRepo;
	@Autowired
	SafeRepo safeRepo;
	@Autowired
	SafeTracingRepo safeTracingRepo;
	@Autowired
	SeasonRepo seasonRepo;
	@Autowired
	SellerLoanBagRepo sellerLoanBagRepo;
	@Autowired
	SellerOrderRepo sellerOrderRepo;
	@Autowired
	SellerRepo sellerRepo;
	@Autowired
	SellerOrderWeightRepo sellerOrderWeightRepo;
	@Autowired
	StoreRepo storeRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	VehicleTypeRepo vehicleTypeRepo;
	
	//@Autowired
	//SafeOfDayRepo safeOfDayRepo;
	

}
