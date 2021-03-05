package com.gomalmarket.shop.core.Enum;
 

public enum LoanTypeEnum  implements EnumClass<String> {
 
	OUT_LOAN("OUT_LOAN"),
	IN_LOAN("IN_LOAN");
	
	
	
	LoanTypeEnum(String value) {
        this.id = value;
    }

    private String id;

	  public String getId() {
	        return id;
	    }

	     
	    public static LoanTypeEnum fromId(String id) {
	        for (LoanTypeEnum at : LoanTypeEnum.values()) {
	            if (at.getId().equals(id)) {
	                return at;
	            }
	        }
	        return null;
	    }
}
