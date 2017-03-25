package com.trl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trl.exception.CopyNotFoundException;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;
import com.trl.stdlib.StdIn;
import com.trl.stdlib.StdOut;

public class CheckInController extends Controller{
	private Patron patronTransacted;
	private List<Copy> copiesToCheckIn;
	private final static Logger loggerIn = LogManager.getLogger(RentalApp.LOGGER_CHECKIN_NAME);
	
	public CheckInController(DataStore ds) {
		super(ds);
	}
	
	@Override
	public boolean startTransaction(Patron patron) throws TransactionAlreadyInProgress {
		
		if (this.patronTransacted !=null) {
			//existing transaction in progress!
			throw new TransactionAlreadyInProgress("there is already a transaction in progress.");
		}
		this.patronTransacted = patron;
		this.copiesToCheckIn = new ArrayList<Copy>();
		return true;
	}
	
	@Override
	public boolean endTransaction(Patron patron) throws NoTransactionInProgress{
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		//commit check out books..
		for (Copy copy : copiesToCheckIn) {
			this.patronTransacted.checkCopyIn(copy);
		}
		this.patronTransacted=null;
		return true;
	}
	
	public void addCopyToCheckin(String copyID)  throws NoTransactionInProgress, CopyNotFoundException{
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		if (!this.dataStore.containsCopy(copyID)) {
			throw new CopyNotFoundException("Copy : " + copyID + " not found");
		}
		this.copiesToCheckIn.add(this.dataStore.getCopy(copyID));
	}
	
	public void checkInBooks() {

		boolean done = false;
		try {
			while (!done){
				StdOut.println("\nPlease enter the copyID to check in");
				String copyID = StdIn.readLine();
				addCopyToCheckin(copyID);
				loggerIn.info("added book to check in " + copyID);
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
			StdOut.println("more books to check in?  type 'Y' or 'N'");
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
}
