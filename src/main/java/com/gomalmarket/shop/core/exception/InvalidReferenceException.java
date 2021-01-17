package com.gomalmarket.shop.core.exception;

/**
 * @author YeHia
 *
 */

/**
 * 
 * this class is the exception for an empty result set in case of searching
 * by id
 *
 */
public class InvalidReferenceException extends Exception{

	final static long serialVersionUID = 4;
	/**
	 * 
	 * @param arg0
	 */
	public InvalidReferenceException(String arg0) {
		super(arg0);
	}
}
