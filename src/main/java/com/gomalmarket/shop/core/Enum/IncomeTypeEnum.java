package com.gomalmarket.shop.core.Enum;

public enum IncomeTypeEnum implements EnumClass<Integer> {
	
	
  CASH(1),
  IN_PAY_LOAN(4),
  INTST_PAY(2),
  IN_LOAN(3);
	IncomeTypeEnum(Integer value) {
		this.id = value;
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public static IncomeTypeEnum fromId(String id) {
		for (IncomeTypeEnum at : IncomeTypeEnum.values()) {
			if (at.getId().equals(id)) {
				return at;
			}
		}
		return null;
	}

}
