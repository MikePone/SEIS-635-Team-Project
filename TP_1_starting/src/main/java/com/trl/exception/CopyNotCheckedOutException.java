package com.trl.exception;

public class CopyNotCheckedOutException extends Exception {

	public CopyNotCheckedOutException(String text) {
		super(text);
	}
}
