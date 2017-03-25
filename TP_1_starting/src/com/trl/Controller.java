package com.trl;

import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;

public abstract class Controller {
	protected final DataStore dataStore;
	
	public Controller(DataStore ds) {
		this.dataStore=ds;
	}
	
	public abstract boolean startTransaction(Patron patron) throws TransactionAlreadyInProgress;
	
	public abstract boolean endTransaction(Patron patron) throws NoTransactionInProgress;
	
}
