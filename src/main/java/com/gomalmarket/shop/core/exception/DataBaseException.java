package com.gomalmarket.shop.core.exception;

/**
 * 
 * @author YeHia
 *
 */
/**
 * 
 * this class is responsible for any exception thrown 
 * by the ORM or the DB
 *
 */
public class DataBaseException extends Exception
{
	final static long serialVersionUID = 6;
	
	public DataBaseException(String arg0) 
	{
		super(arg0);
	}
	 

}
