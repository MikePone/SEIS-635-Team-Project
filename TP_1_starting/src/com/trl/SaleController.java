package com.trl;

import java.util.List;

import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;

public class SaleController extends Controller{
	private Patron patronTransacted;
	private List<Copy> copiesToCheckOut;
	
	public SaleController(DataStore ds) {
		super(ds);
	}
	
	@Override
	public boolean startTransaction(Patron patron) throws TransactionAlreadyInProgress {
		
		if (this.patronTransacted !=null) {
			//existing transaction in progress!
			throw new TransactionAlreadyInProgress("there is already a transaction in progress.");
		}
		this.patronTransacted = patron;
		return true;
	}
	
	@Override
	public boolean endTransaction(Patron patron) throws NoTransactionInProgress{
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		//commit check out books..
		for (Copy copy : copiesToCheckOut) {
			this.patronTransacted.checkCopyOut(copy);
		}
		this.patronTransacted=null;
		return true;
	}
	
	public void addCopyToCheckout(Copy copy)  throws NoTransactionInProgress{
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		this.copiesToCheckOut.add(copy);
	}
}
