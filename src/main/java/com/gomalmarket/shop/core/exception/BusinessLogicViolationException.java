package com.gomalmarket.shop.core.exception;

/**
 * @author YeHia
 *
 */

/**
 * 
 * this class is the exception for any business case violation
 *
 */
public class BusinessLogicViolationException extends Exception{

	final static long serialVersionUID = 10;
	/**
	 * 
	 * @param arg0
	 */
	public BusinessLogicViolationException(String arg0) {
		super(arg0);
	}
}
