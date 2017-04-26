package com.trl;

import java.util.ArrayList;
import java.util.Date;

import com.trl.stdlib.StdOut;

public class Patron
{
	private final String name;
	private final String patronID;
	private final ArrayList<Copy> copiesOut;
	private final ArrayList<Hold> patronHolds;

	public Patron(String n, String id)
	{
		if( n != null)
		{
			this.name = n;
			this.patronID = id;
			this.copiesOut = new ArrayList<Copy>();
			this.patronHolds = new ArrayList<Hold>();
		}
		else
		{
			throw new IllegalArgumentException("Patron Name cannot be empty");
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public void addHold(Hold newHold){
		this.patronHolds.add(newHold);
	}
	
	public void removeHold(Hold oldHold){
		this.patronHolds.remove(oldHold);
	}
	
	public boolean hasHolds(){
		return !this.patronHolds.isEmpty();
	}

	public boolean hasCopyCheckedOut(Copy c){
		return this.copiesOut.contains(c);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((patronID == null) ? 0 : patronID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Patron other = (Patron) obj; 
		
		if (!name.equals(other.name)) return false;
		if (patronID == null) {
			if (other.patronID != null)
				return false;
		} else if (!patronID.equals(other.patronID))
			return false;
		return true;
	}

	public boolean checkCopyOut(Copy c, Date dueDate)  
	{
		c.setOutTo(this, dueDate);
		copiesOut.add(c);
		return true;
	}

	public boolean checkCopyIn(Copy c) 
	{
		c.setOutTo(null, null);
		if (copiesOut.contains(c))
		{
			copiesOut.remove(c);
			return true;
		}
		else
			return false;
	}

	public String toString()
	{
		String toReturn = "Patron w/ name: " + this.name + ", id: " + this.patronID;

		if (this.copiesOut.isEmpty())
		{
			toReturn = toReturn + "\nNo copies checked out.\n";
		}
		else
			for (Copy copy : this.copiesOut)
			{
				toReturn = toReturn + "\nCopies checked out:";
				toReturn = toReturn + "\n\t" + copy.toString() + "\n";
			}

		return toReturn;
	}

	public void printPatron()
	{
		StdOut.println("Patron w/ name: " + this.name + ", id: " + this.patronID);
	}
	
	public static void main(String[] args)
	{
		Patron p1 = new Patron("James", "007");

		System.out.println(p1);
	}

	public String getPatronID() {
		return patronID;
	}

	public ArrayList<Copy> getCopiesOut() {
		return copiesOut;
	}

	public ArrayList<Hold> getPatronHolds() {
		return patronHolds;
	}

	
}
