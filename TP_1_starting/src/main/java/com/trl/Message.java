package com.trl;

public class Message {
	private String theMessage;

	public Message(String text) {
		this.theMessage=text;
	}

	public Message addMessage(String msg) {
		theMessage+="/n"+ msg;
		return this;
	}
	public String getTheMessage() {
		return theMessage;
	}
	
	
}
