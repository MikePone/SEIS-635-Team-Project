package com.trl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trl.exception.CopyNotFoundException;
import com.trl.exception.HasHoldsException;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;
import com.trl.stdlib.StdIn;
import com.trl.stdlib.StdOut;

public class CheckOutController extends Controller{
	private Patron patronTransacted;
	private List<Copy> copiesToCheckOut;
	private final static Logger loggerIn = LogManager.getLogger(RentalApp.LOGGER_CHECKIN_NAME);
	//This due date should be flexible and should change with each semester.
	//The due date should be about 1 week after the semester ends to give students time to check the books back in.
	private final Date dueDate = new Date(1495256400000l); //May 20 2017 12:00 AM
	
	public CheckOutController(DataStore ds) {
		super(ds);
	}
	
	public void checkOutBooks() {
		// Alert Staff if Patron has existing hold
		if (this.patronTransacted.hasHolds())
		{
			loggerIn.info("ALERT : THIS PATRON HAS HOLD, OUTSTANDING DUES " + this.patronTransacted);
			StdOut.println("-------******----------"); 
			StdOut.println("ALERT : THIS PATRON HAS HOLD, OUTSTANDING DUES : " + this.patronTransacted);
			StdOut.println("-------******----------"); 
		}
		boolean done = false;
		try {
			while (!done){
				
				StdOut.println("\nPlease enter the copyID to check out");
				String copyID = StdIn.readLine(); 
			    // check if book exist in the system list.
				if (!dataStore.containsCopy(copyID)){
					StdOut.println("\ncopyID " + copyID +" not found!");
					throw new CopyNotFoundException("Copy : " + copyID + " not found");
				}
				Copy checkOutCopy = dataStore.getCopy(copyID);
				addCopyToCheckout(checkOutCopy);
				
				loggerIn.info("Book checked out : " + copyID);
				// allow multiple check out ; user input
				if (moreBooks()) {
					continue;
				}else {
					done=true;
				}

			}
		} catch (NoTransactionInProgress e) {
			loggerIn.info("no transaction in progress",e);
			StdOut.println("cannot add a book, a transaction is not started");
		} catch (CopyNotFoundException e) {
			loggerIn.info("copy not found : ",e);
			StdOut.println("cannot add a book, it wasn't in the database");
		}
	}
	
	private boolean moreBooks(){
		boolean done =false;
		boolean returnVal=false;
		
		while (!done) {
			StdOut.println("more books to check out?  type 'Y' or 'N'");
			//if done, set done to true
			String moreBooks = StdIn.readLine();
			
			if ("N".equalsIgnoreCase(moreBooks)){
				done=true;
				returnVal=false;
			}else if ("Y".equalsIgnoreCase(moreBooks)){
				done=true;
				returnVal=true;
			}else {
				StdOut.println("unrecognized option");
			}
		}
		return returnVal;
	}
	
	@Override
	public boolean startTransaction(Patron patron) throws TransactionAlreadyInProgress,HasHoldsException {
		
		if (this.patronTransacted !=null) {
			//existing transaction in progress!
			throw new TransactionAlreadyInProgress("there is already a transaction in progress.");
		}
		//validate - if patron has hold, they cannot checkout
		if (patron.hasHolds()) {
			throw new HasHoldsException("cannot check out a book with holds on account.");
		}
		this.patronTransacted = patron;
		this.copiesToCheckOut = new ArrayList<Copy>();
		return true;
	}
	
	@Override
	public boolean endTransaction(Patron patron) throws NoTransactionInProgress{
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		//commit check out books..
		// adding copy to the partron upon ending the transaction.
		for (Copy copy : copiesToCheckOut) {
			this.patronTransacted.checkCopyOut(copy, dueDate);
		}
		this.patronTransacted=null;
		return true;
	}
	
	private void addCopyToCheckout(Copy copy)  throws NoTransactionInProgress{
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		this.copiesToCheckOut.add(copy); // add copy to checkout
	}
	
}
