package com.gomalmarket.shop.core.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author YeHia
 *
 */

/**
 * 
 * this class is the data structure to represent field exception
 *
 */
public class ExceptionStackTrace 
{
	private List<Object> exceptionList;
		
	public ExceptionStackTrace()
	{
		this.exceptionList = new ArrayList<Object>();
	}
	
	public void addNewException(String fieldId, String errorMessage)
	{
		this.exceptionList.add(new FieldException(fieldId,errorMessage));
	}

	public List<Object> getExceptionList() {
		return exceptionList;
	}
	
}
