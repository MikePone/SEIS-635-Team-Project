package com.trl.exception;

public class TransactionAlreadyInProgress extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransactionAlreadyInProgress(String message) {
		super(message);
	}
}
