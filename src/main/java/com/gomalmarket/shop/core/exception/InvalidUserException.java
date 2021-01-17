
package com.gomalmarket.shop.core.exception;

/**
 * @author fangelo
 *
 */

/**
 * 
 * this class is the exception for invalid user returned from the session
 *
 */
public class InvalidUserException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8114948052710702447L;
	
	/**
	 * 
	 * @param arg0
	 */
	public InvalidUserException(String arg0) {
		super(arg0);
	}
	
}
