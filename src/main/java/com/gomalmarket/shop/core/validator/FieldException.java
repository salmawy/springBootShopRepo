package com.gomalmarket.shop.core.validator;


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
public class FieldException 
{
	private String fieldId;
	private String errorMessage;
	
	public FieldException(String fieldId, String errorMessage) 
	{
		this.fieldId = fieldId;
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	

	public String getFieldId() {
		return fieldId;
	}
		
}
