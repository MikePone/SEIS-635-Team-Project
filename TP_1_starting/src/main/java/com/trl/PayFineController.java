package com.trl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trl.Hold.HOLD_REASON;
import com.trl.exception.CopyNotFoundException;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;
import com.trl.stdlib.StdIn;
import com.trl.stdlib.StdOut;

public class PayFineController extends Controller{
	private Patron patronTransacted;
	private List<Copy> copiesToCheckIn;
	private List<Textbook> prices;
	private final static Logger loggerIn = LogManager.getLogger(RentalApp.LOGGER_CHECKIN_NAME);
	
	public PayFineController(DataStore ds) {
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
		this.patronTransacted=null;
		return true;
	}
	
	private double calculateAmount(Copy c)  throws NoTransactionInProgress, CopyNotFoundException{
		if (this.patronTransacted == null) {
			throw new NoTransactionInProgress("no transaction in progress");
		}
		return c.getTextbook().getPrice();
	}
	
	public void payFine() 
	{
		Hold newHold = new Hold(new Copy("001",  new Textbook("id", 1, "ISBN", "author", "title", "edition")), this.patronTransacted, HOLD_REASON.UnpaidFine);
		this.patronTransacted.addHold(newHold);
		StdOut.println("Hold added to patron " + this.patronTransacted.getName() + " Currently on hold - fine due for unpaid fine ");
		loggerIn.info("Hold added to patron " + this.patronTransacted.getName() + " Currently on hold - fine due for unpaid fine ");
		StdOut.println("\nPlease enter the amount to pay"); //this amount is dummy, no amount functionlity
		String amount = StdIn.readLine();
		
		this.patronTransacted.removeHold(newHold);;
		StdOut.println("Hold removed from patron " + this.patronTransacted.getName());
		loggerIn.info("Hold removed from patron " + this.patronTransacted.getName());
	}
	
	
}
