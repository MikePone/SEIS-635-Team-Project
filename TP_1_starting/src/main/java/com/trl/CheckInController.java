package com.trl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trl.exception.CopyNotCheckedOutException;
import com.trl.exception.CopyNotFoundException;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress; 

public class CheckInController extends Controller{
	private Patron patronTransacted;
	private List<Copy> copiesToCheckIn;
	private final static Logger loggerIn = LogManager.getLogger(RentalApp.LOGGER_CHECKIN_NAME);
	private final RentalAppView view;
	
	public CheckInController(DataStore ds, RentalAppView view) {
		super(ds);
		this.view=view;
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
		}//need to revisit; we don't require Patron object in the method
		//commit check out books..
		// adding copy to the partron upon ending the transaction.
		for (Copy copy : copiesToCheckIn) {
			this.patronTransacted.checkCopyIn(copy);
		}
		this.patronTransacted=null;
		return true;
	}
	
	private void addCopyToCheckin(String copyID)  throws NoTransactionInProgress, CopyNotFoundException, CopyNotCheckedOutException{
		//validation the transaction session is valid
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		//validation the transaction session is valid
		if (!this.dataStore.containsCopy(copyID)) {
			throw new CopyNotFoundException("Copy : " + copyID + " not found");
		}
		Copy copy = this.dataStore.getCopy(copyID);
		//Is the copy checked out to this user?
		//validation the copy is checkout out to this patron is valid
		if (this.patronTransacted.hasCopyCheckedOut(copy)) {
			this.copiesToCheckIn.add(copy);// add the copy checked in
		}else {
			throw new CopyNotCheckedOutException("Copy Not Checked Out to Patron.");
		}
			
	}
	
	public void checkInBooks() {
		// Alert Staff if Patron has existing hold
		if (this.patronTransacted.hasHolds())
		{
			loggerIn.info("ALERT : THIS PATRON HAS HOLD, OUTSTANDING DUES " + this.patronTransacted);
			view.showMessage(
					new Message("-------******----------")
					.addMessage("ALERT : THIS PATRON HAS HOLD, OUTSTANDING DUES : " + this.patronTransacted)
					.addMessage("-------******----------"));
		}

		boolean done = false;
		try {
			while (!done){
				String copyID = view.showMessageWithInput(new Message("Please enter the copyID to check in"));
			    // check if book exist in the system list.
				if (!dataStore.containsCopy(copyID)){
					view.showMessage(new Message("copyID " + copyID +" not found!"));
					throw new CopyNotFoundException("Copy : " + copyID + " not found");
				}
				try {
					addCopyToCheckin(copyID);
				} catch (CopyNotCheckedOutException e) {
					loggerIn.info("Copy Not checked out to Patron: " + copyID);
					view.showMessage(new Message("CopyID " + copyID +" not checked out to Patron!"));
				}
				loggerIn.info("added book to check in " + copyID);
				// allow multiple check in ; user input
				if (moreBooks()) {
					continue;
				}else {
					done=true;
				}

			}
		} catch (NoTransactionInProgress e) {
			loggerIn.info("no transaction in progress",e);
			view.showMessage(new Message("cannot add a book, a transaction is not started")); 
		} catch (CopyNotFoundException e) {
			loggerIn.info("copy not found : ",e);
			view.showMessage(new Message("cannot add a book, it wasn't in the database"));  
		}
	}
	
	private boolean moreBooks(){
		boolean done =false;
		boolean returnVal=false;
		
		while (!done) {
			String moreBooks = view.showMessageWithInput(new Message("more books to check in?  type 'Y' or 'N'"));
			
			if ("N".equalsIgnoreCase(moreBooks)){
				done=true;
				returnVal=false;
			}else if ("Y".equalsIgnoreCase(moreBooks)){
				done=true;
				returnVal=true;
			}else {
				view.showMessage(new Message("unrecognized option : " + moreBooks));  
			}
		}
		return returnVal;
	}
}
