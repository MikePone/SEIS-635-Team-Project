package com.trl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trl.exception.CopyNotFoundException;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;
import com.trl.stdlib.StdIn;
import com.trl.stdlib.StdOut;

public class SellCopyController extends Controller{
	private Patron patronTransacted;
	private List<Copy> copiesToCheckIn;
	private List<Textbook> prices;
	private final static Logger loggerIn = LogManager.getLogger(RentalApp.LOGGER_CHECKIN_NAME);
	private final RentalAppView view;

	public SellCopyController(DataStore ds, RentalAppView view) {
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
		this.prices = new ArrayList<Textbook>();
		return true;
	}
	
	@Override
	public boolean endTransaction(Patron patron) throws NoTransactionInProgress{
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		this.patronTransacted=null;
		return true;
	}
	
	private BigDecimal calculateAmount(Copy c)  throws NoTransactionInProgress, CopyNotFoundException{
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		//returns price of the book set as part of mock up in datastore
		return c.getTextbook().getPrice();
	}
	
	public void doSale() {

		boolean done = false;
		try {
			while (!done){
				String copyID = view.showMessageWithInput(new Message("Please enter the copyID to sell"));

				//validate the copy is valid 
				if (!this.dataStore.containsCopy(copyID)) {
					view.showMessage(new Message("copyID " + copyID +" not found!"));
					throw new CopyNotFoundException("Copy : " + copyID + " not found");
				}
				Copy c = this.dataStore.getCopy(copyID);
				
				//calculate the copy price
				BigDecimal amount = calculateAmount(c);
				
				//remove after copy is sold from dataStore copy list
				this.dataStore.removeCopy(c);
				view.showMessage(new Message("The book " + copyID+ " is sold to " + this.patronTransacted.getName() + " for the amount of " + amount));
				loggerIn.info("sold book " + copyID + " to " + this.patronTransacted.getName());
				
				// allow multiple sell copy; user input
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
			StdOut.println("cannot find the book, it wasn't in the database");
		}
	}
	
	private boolean moreBooks(){
		boolean done =false;
		boolean returnVal=false;
		
		while (!done) {
			String moreBooks = view.showMessageWithInput(new Message("more books to sell?  type 'Y' or 'N'"));
			//if done, set done to true
			
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
