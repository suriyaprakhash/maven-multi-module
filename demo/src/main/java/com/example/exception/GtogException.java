package com.example.exception;

/**
 * This is the generic Gtog exception throw when an excpetion happens due to
 * invalid data or an actual exception
 * 
 * @version
 *
 */
public class GtogException extends Exception {

	public GtogException() {
		super();
	}

	public GtogException(String string) {
		super(string);
	}

	public GtogException(String string, Throwable e) {
		super(string, e);
	}

}
