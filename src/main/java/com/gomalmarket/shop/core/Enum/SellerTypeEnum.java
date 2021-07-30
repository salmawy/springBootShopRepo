package com.gomalmarket.shop.core.Enum;

public enum SellerTypeEnum implements EnumClass<Integer> {
	
	  permenant(2),
 cash(1);

	SellerTypeEnum(Integer value) {
		this.id = value;
	}

	private final Integer id;

	public Integer getId() {
		return id;
	}

	public static SellerTypeEnum fromId(String id) {
		for (SellerTypeEnum at : SellerTypeEnum.values()) {
			if (at.getId().equals(Integer.parseInt(id))) {
				return at;
			}
		}
		return null;
	}


}
