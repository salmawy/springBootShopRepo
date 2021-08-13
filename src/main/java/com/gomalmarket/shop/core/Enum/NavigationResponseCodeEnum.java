package com.gomalmarket.shop.core.Enum;
 
public enum NavigationResponseCodeEnum implements EnumClass<String> {
	
	 SUCCESS("msg.done.save",100),
  SAVED("msg.done.save",200),
  EDITED("msg.done.edit",201),
  CANNOT_LOAD_DATA("",504),
  GENERAL_ERROR("msg.err.general",500),
  DATABASE_ERROR("msg.err.cannot.load.data",503),
  FAIL("msg.err",502),
 EXIT("msg.err",400);
	NavigationResponseCodeEnum(String value,int code) {
		this.id = value;
	}

	private String id;
	private int code;

	public String getId() {
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
