package com.gomalmarket.shop.core.exception;

/**
 * @author YeHia
 *
 */

/**
 * 
 * this class is the exception for an empty search result
 *
 */
public class EmptyResultSetException extends Exception{

	final static long serialVersionUID = 2;
	/**
	 * 
	 * @param arg0
	 */
	public EmptyResultSetException(String arg0) {
		super(arg0);
	}
}
