package com.gomalmarket.shop.core.exception;

/**
 * @author YeHia
 *
 */

/**
 * 
 * this class is the exception for the date, integer and float out of range
 *
 */
public class OutOfRangeException extends Exception{

	final static long serialVersionUID = 1;
	/**
	 * 
	 * @param arg0
	 */
	public OutOfRangeException(String arg0) {
		super(arg0);
	}
}
