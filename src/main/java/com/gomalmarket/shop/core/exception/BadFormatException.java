package com.gomalmarket.shop.core.exception;

/**
 * @author YeHia
 *
 */

/**
 * 
 * this class is the exception for the date, integer and float bad format
 *
 */
public class BadFormatException extends Exception{

	final static long serialVersionUID = 0;
	/**
	 * 
	 * @param arg0
	 */
	public BadFormatException(String arg0) {
		super(arg0);
	}
}
