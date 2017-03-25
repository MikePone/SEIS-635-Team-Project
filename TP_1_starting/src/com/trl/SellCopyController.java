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

public class SellCopyController extends Controller{
	private Patron patronTransacted;
	private List<Copy> copiesToCheckIn;
	private List<Textbook> prices;
	private final static Logger loggerIn = LogManager.getLogger(RentalApp.LOGGER_CHECKIN_NAME);
	
	public SellCopyController(DataStore ds) {
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
		this.prices = new ArrayList<Textbook>();
		return true;
	}
	
	@Override
	public boolean endTransaction(Patron patron) throws NoTransactionInProgress{
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		return true;
	}
	
	public double calculateAmount(String copyID)  throws NoTransactionInProgress, CopyNotFoundException{
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		if (!this.dataStore.containsCopy(copyID)) {
			throw new CopyNotFoundException("Copy : " + copyID + " not found");
		}
		
		for (Textbook price : dataStore.getPrices().values()) 
		{
			if (price.getCopyID().equals(copyID))
			{
				//remove the sold copy 
				dataStore.getCopies().remove(copyID);
				return price.getPrice(); // return the book price
			}
		}
		return (Double) null;
	}
	
	private String copyID() {
		// TODO Auto-generated method stub
		return null;
	}

	public void getPrice() {

		boolean done = false;
		try {
			while (!done){
				StdOut.println("\nPlease enter the copyID to sell");
				String copyID = StdIn.readLine();
				double amount = calculateAmount(copyID);
				StdOut.println("The book " + copyID+ " is sold to " + this.patronTransacted.getName() + " for the amount of " + amount);
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
			StdOut.println("cannot find the book, it wasn't in the database");
		}
	}
	
	private boolean moreBooks(){
		boolean done =false;
		boolean returnVal=false;
		
		while (!done) {
			StdOut.println("more books to Sell?  type 'Y' or 'N'");
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
