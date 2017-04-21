package com.trl.exception;

public class CopyNotCheckedOutException extends Exception {

	private static final long serialVersionUID = 1L;

	public CopyNotCheckedOutException(String text) {
		super(text);
	}
}
