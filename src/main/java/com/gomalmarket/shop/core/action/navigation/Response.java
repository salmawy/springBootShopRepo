package com.gomalmarket.shop.core.action.navigation;

import java.util.Map;

import com.gomalmarket.shop.core.Enum.NavigationResponseCodeEnum;

public interface Response {
	public String RESPONSE_CODE_KEY = "code";
	public String RESPONSE_MSG_KEY = "msg";
 
 	public NavigationResponseCodeEnum getResponseCode();
	public Map getResults();
	
	 
}
