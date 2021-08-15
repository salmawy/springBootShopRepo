package com.gomalmarket.shop.core.action.navigation;

import java.util.Map;

public interface Request {
	public int MODE_CREATE_NEW = 0;
	public int MODE_ADD = 1;
	public int MODE_EDIT = 2;
	public int MODE_DELETE = 3;
 
	public int getMode();

	public Object getEditedObject();
	public int getEditedObjectId();
	public Map getMap();
}
