/**
 * 
 */
package com.gomalmarket.shop.core.exception;

/**
 * @author YeHia
 *
 */
public class DivideByZeroException extends Exception
{
	final static long serialVersionUID = 5;
	
    public DivideByZeroException()
    {
        super("Dividing by Zero!");
    }

    public DivideByZeroException(String message)
    {
        super(message);
    }
}