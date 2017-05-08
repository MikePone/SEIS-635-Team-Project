package com.trl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trl.Hold.HOLD_REASON;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;
import com.trl.stdlib.StdIn;
import com.trl.stdlib.StdOut;

public class ManageHoldController extends Controller{
	private Patron patronTransacted;
	private List<Copy> copiesToCheckIn;
	private List<Textbook> prices;
	private final static Logger loggerIn = LogManager.getLogger(RentalApp.LOGGER_CHECKIN_NAME);
	
	public ManageHoldController(DataStore ds) {
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
	
	//allow rental staff to manage hold, add or remove existing hold
	public void manageHold() {
		StdOut.println("\nManage hold for patron " + this.patronTransacted.toString());

		boolean exit = false;
			while (!exit){
				StdOut.println("Please enter the number next to what you want to do");
				StdOut.println("1 : Remove Hold");
				StdOut.println("2 : Add hold");
				StdOut.println("3 : Exit");
				String in = StdIn.readLine();
				
				switch (in) {
					case "1":
						Hold currentHold = null;
						//get the current hold for the patron
						for (Hold newHold : this.patronTransacted.getPatronHolds())
						{
							currentHold = newHold;
							break;
						}

						this.patronTransacted.removeHold(currentHold); // removing the hold
						loggerIn.info("The hold is removed of reason : " + currentHold.getReason());
						StdOut.println("The hold is removed of reason : " + currentHold.getReason());
						currentHold.getHoldPatron().printPatron();
						break;

					//add new hold to the Patron with mock hold data.
					case "2" :
						//create mock new hold object
						Hold newHold = new Hold(new Copy("001",  new Textbook("id", new BigDecimal("1"), "ISBN", "author", "title", "edition")), this.patronTransacted, HOLD_REASON.UnpaidFine);
						this.patronTransacted.addHold(newHold); // add hold
						loggerIn.info("The hold is added - reason :" + newHold.getReason() + newHold.getHoldPatron().toString());
						StdOut.println("The hold is added - reason :" + newHold.getReason() + newHold.getHoldPatron().toString());
						break;
						
					case "3" :
						exit=true;
						break;
					default: // action is unrecognized.
						StdOut.println("unrecocognized option:" + in);
					}
				}
	}
}
