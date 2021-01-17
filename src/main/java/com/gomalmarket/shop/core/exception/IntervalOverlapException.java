package com.gomalmarket.shop.core.exception;

public class IntervalOverlapException extends Exception
{
	final static long serialVersionUID = 7;
	
	public IntervalOverlapException() {
		super();
	}

	public IntervalOverlapException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IntervalOverlapException(String arg0) {
		super(arg0);
	}

	public IntervalOverlapException(Throwable arg0) {
		super(arg0);
	}

	
}
