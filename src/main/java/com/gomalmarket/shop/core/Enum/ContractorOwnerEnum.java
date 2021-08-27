package com.gomalmarket.shop.core.Enum;
 public enum ContractorOwnerEnum  implements EnumClass<Integer> {
 
	KAREEM(1,"label.owner.name.kareem"),
	MOHAMED(2,"label.owner.name.mahmed");
	
	
	  private Integer id;
	  private String label;

	ContractorOwnerEnum(Integer value,String label) {
        this.id = value;
        this.setLabel(label);
    }

  
	  public Integer getId() {
	        return id;
	    }

	     
	    public static ContractorOwnerEnum fromId(Integer id) {
	        for (ContractorOwnerEnum at : ContractorOwnerEnum.values()) {
	            if (at.getId().equals(id)) {
	                return at;
	            }
	        }
	        return null;
	    }


		public String getLabel() {
			return label;
		}


		public void setLabel(String label) {
			this.label = label;
		}
}
