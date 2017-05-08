package com.trl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trl.Hold.HOLD_REASON;

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
			String copyId = "Copy" + (i+1);
			String bookId = "Book" + (i+1);
			String author = "Author" + (i+1);
			String title = "Title" + (i+1);
			String ISBN = "001-2-" + (i+1);
			Textbook book = new Textbook(copyId, new BigDecimal(100+i), ISBN,author,title,"Edition1");
			
			Copy copy = new Copy("Copy" + (i+1),book);
			if (i >7)//set due dates 
			{
				Date date = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L);
				copy.setDueDate(date);
			}
			copies.put(copyId, copy); // add copy to the set of copies.
			
			prices.put(bookId, book);
	      }
		
		//Create a hold for PatronId > 7 
		for (int i = 0; i < 10; i++) 
	      {        
			if (i >7)
			{
				Patron holdPatron = patrons.get("00"+i);
				Copy holdCopy = copies.get("Copy"+i);
				holdPatron.checkCopyOut(holdCopy, new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L));
				holdPatron.addHold(new Hold(holdCopy, holdPatron, HOLD_REASON.OverdueBook));			}
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
	
	public Textbook geTextbook(String bookId) {
		return this.prices.get(bookId);
	}

	public boolean removeCopy(Copy c) {
		return this.copies.remove(c.getCopyID())!=null ;
	}
	
	public List<Patron> getPatronList() {
		return new ArrayList<>(this.patrons.values());
	}

	public List<Copy> getCopyList() {
		return new ArrayList<>(this.copies.values());
	}
	
}
