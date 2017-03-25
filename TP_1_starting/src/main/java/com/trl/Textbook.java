package com.trl;


public class Textbook
{
	private String copyID;
	private double price;
	
	public Textbook(String copyID, double price) {
		super();
		this.copyID = copyID;
		this.price = price;
	}
	public String getCopyID() {
		return copyID;
	}
	public void setCopyID(String copyID) {
		this.copyID = copyID;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	

	
}
