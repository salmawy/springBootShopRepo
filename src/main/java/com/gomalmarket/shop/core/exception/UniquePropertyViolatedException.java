package com.gomalmarket.shop.core.exception;

/**
 * @author YeHia
 *
 */

/**
 * 
 * this class is the exception for the krimary key violated
 *
 */
public class UniquePropertyViolatedException extends Exception{

	final static long serialVersionUID = 9;
	/**
	 * 
	 * @param arg0
	 */
	public UniquePropertyViolatedException(String arg0) {
		super(arg0);
	}
}
