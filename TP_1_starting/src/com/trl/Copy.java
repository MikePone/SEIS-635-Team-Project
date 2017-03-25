package com.trl;


public class Copy
{
	private final String copyID;
	private Patron outTo;
	private Textbook price;

	// following generated in Eclipse Source menu

	public Patron getOutTo()
	{
		return outTo;
	}

	public void setOutTo(Patron outTo)
	{
		this.outTo = outTo;
	}

	public String getCopyID()
	{
		return copyID;
	}

	public Copy(String cid)
	{
		this.copyID = cid;
	}

	public String toString()
	{
		return "Copy w/id= " + this.copyID;
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Copy)) return false;
		
		Copy otherCopy =(Copy) o; 
		return otherCopy.getCopyID().equals(this.copyID); // yuck.
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((copyID == null) ? 0 : copyID.hashCode());
		return result;
	}
	public static void main(String[] args)
	{
		Copy c1 = new Copy("0047");
		Patron p1 = new Patron("James", "008");

		System.out.println(c1);
		System.out.println(p1);
	}
	
	
}
