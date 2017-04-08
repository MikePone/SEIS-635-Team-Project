package com.trl;

import com.trl.exception.HasHoldsException;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;

public abstract class Controller {
	protected final DataStore dataStore;
	
	public enum ACTION {
		CheckOut("1"), 
		CheckIn("2"), 
		SellBook("3"),
		ViewPatron("4"),
		ListBooks("5"), 
		ListUsers("6"),
		Exit("7");
		
		private String value;
		ACTION(String val) {
			this.value=val;
		}
		
		public static ACTION fromString(String text) {
		    for (ACTION b : ACTION.values()) {
		      if (b.value.equalsIgnoreCase(text)) {
		        return b;
		      }
		    }
		    return null;
		  }
	}
	
	public Controller(DataStore ds) {
		this.dataStore=ds;
	}
	
	public abstract boolean startTransaction(Patron patron) throws TransactionAlreadyInProgress, HasHoldsException;
	
	public abstract boolean endTransaction(Patron patron) throws NoTransactionInProgress;
	
}
