package com.trl;

import java.util.HashMap;
import java.util.Map;

public class DataStore {

	private final Map<String, Patron> patrons = new HashMap<String, Patron>(); 
	private final Map<String, Copy> copies = new HashMap<String, Copy>();  
	private final Map<String, Textbook> prices = new HashMap<String, Textbook>();  
	
	public DataStore() {

		//load users; Initialize with 10 Patrons
		for (int i = 0; i < 10; i++) 
	      {        
			String name = "Patron" + (i+1);
			String id = String.format("%03d", (i+1));
			Patron patron = new Patron(name, id);
			patrons.put(id, patron); // add patron to the set of patrons.
		}

		//load books; Initialize with 10 Copy
		//load books Price; 
		for (int i = 0; i < 10; i++) 
	      {        
			String id = "Copy" + (i+1);
			Copy copy = new Copy("Copy" + (i+1));
			copies.put(id, copy); // add copy to the set of copies.
			
			Textbook price = new Textbook(copy.getCopyID(), 100+i);
			prices.put(id, price);
	      }
		
		
	}
	
	public boolean containsPatron(String patronID) {
		return this.patrons.containsKey(patronID);
	}
	
	public Patron getPatron(String patronID) {
		return this.patrons.get(patronID);
	}

	public boolean containsCopy(String copyID) {
		return this.copies.containsKey(copyID);
	}

	public Copy getCopy(String copyID) {
		return this.copies.get(copyID);
	}

	public Map<String, Patron> getPatrons() {
		return patrons;
	}

	public Map<String, Copy> getCopies() {
		return copies;
	}

	public Map<String, Textbook> getPrices() {
		return prices;
	}
	
	
	
	
}
