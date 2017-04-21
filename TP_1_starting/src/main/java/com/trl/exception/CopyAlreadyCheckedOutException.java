package com.trl.exception;

public class CopyAlreadyCheckedOutException extends Exception {

	private static final long serialVersionUID = 1L;

	public CopyAlreadyCheckedOutException(String text){
		super(text);
	}
}
