package com.trl;


public class Textbook
{
	private final String copyID;
	//TODO - rethink using double here.  Double is notoriously inaccurate after arithmetic is applied.
	//Maybe BigDecimal.
	private final double price;
	
	public Textbook(String copyID, double price) {
		super();
		this.copyID = copyID;
		this.price = price;
	}
	public String getCopyID() {
		return copyID;
	}
	public double getPrice() {
		return price;
	}
	
}
