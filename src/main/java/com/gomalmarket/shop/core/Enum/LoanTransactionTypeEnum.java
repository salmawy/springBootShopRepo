package com.gomalmarket.shop.core.Enum;
public enum LoanTransactionTypeEnum  implements EnumClass<String> {
 
	PAY_CREDIT("PAY_CREDIT"),
	PAY_DEBIT("PAY_DEBIT"),
	LOAN_CREDIT("lOAN_CREDIT"),
	LOAN_DEBET("lOAN_DEBET");
	
	
	
	LoanTransactionTypeEnum(String value) {
        this.id = value;
    }

    private String id;

	  public String getId() {
	        return id;
	    }

	     
	    public static LoanTransactionTypeEnum fromId(String id) {
	        for (LoanTransactionTypeEnum at : LoanTransactionTypeEnum.values()) {
	            if (at.getId().equals(id)) {
	                return at;
	            }
	        }
	        return null;
	    }
}
