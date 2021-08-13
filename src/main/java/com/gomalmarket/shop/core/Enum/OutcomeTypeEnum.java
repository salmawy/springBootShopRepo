package com.gomalmarket.shop.core.Enum;

public enum OutcomeTypeEnum implements EnumClass<Integer> {

	varaity(1),
	K_L(2),
	OUT_LOAN(3),
	labours(4),
	ORDER_PAY(5),
	allah(6),
	PURCHASES_WITHDRAWALS(7),
	maintaince(8),
	PAY_DEBIT(9),
	K_V(10),
	INVOICE_TIPS(11),
	TIPS(12),
	K_S(13),
	NOLOUN(14),
	forgivness(15);

	OutcomeTypeEnum(Integer value) {
		this.id = value;
	}

	private Integer id;

	public Integer getId() {
		return id;
	}

	public static OutcomeTypeEnum fromId(Integer id) {
		for (OutcomeTypeEnum at : OutcomeTypeEnum.values()) {
			if (at.getId().equals(id)) {
				return at;
			}
		}
		return null;
	}

}
