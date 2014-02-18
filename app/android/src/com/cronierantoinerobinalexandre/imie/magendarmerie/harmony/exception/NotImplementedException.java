/**************************************************************************
 * NotImplementedException.java, magendarmerie Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 15, 2014
 *
 **************************************************************************/
package com.cronierantoinerobinalexandre.imie.magendarmerie.harmony.exception;

/**
 * Exception thrown when a feature hasn't been implemented.
 */
public class NotImplementedException extends RuntimeException {
	/**
	 * Serial Version UID.
	 */
	private static final long	serialVersionUID	= 6531619427489977103L;

	/**
	 * Constructor.
	 * @param message The exception message
	 */
	public NotImplementedException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * @param message The exception message
	 * @param throwable The associated throwable
	 */
	public NotImplementedException(String message, Throwable throwable) {
		super(message, throwable);
	}
}

