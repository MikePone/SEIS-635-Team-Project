package com.trl.exception;

public class CopyAlreadyCheckedOutException extends Exception {

	public CopyAlreadyCheckedOutException(String text){
		super(text);
	}
}
