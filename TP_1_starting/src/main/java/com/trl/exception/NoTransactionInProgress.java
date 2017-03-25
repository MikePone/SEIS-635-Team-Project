package com.trl.exception;

public class NoTransactionInProgress extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoTransactionInProgress(String message) {
		super(message);
	}
}
