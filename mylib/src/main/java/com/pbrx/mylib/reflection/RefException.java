package com.pbrx.mylib.reflection;

public class RefException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8248853556846348815L;

	public RefException() {
		super();
	}

	public RefException(String msg) {
		super(msg);
	}

	public RefException(Throwable throwable) {
		super(throwable);
	}

	public RefException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
