package com.trl;

import java.util.HashMap;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trl.ActionController.ACTION;
import com.trl.exception.CopyNotFoundException;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;
import com.trl.stdlib.StdIn;
import com.trl.stdlib.StdOut; 

public class RentalApp 
{
	public static final String LOGGER_CHECKIN_NAME = "com.trl.checkin";
	public static final String LOGGER_CHECKOUT_NAME = "com.trl.checkout";
	public static final String LOGGER_SELL_NAME = "com.trl.sell";
	private final static DataStore dataStore = new DataStore();

	private static final Logger loggerIn = LogManager.getLogger(LOGGER_CHECKIN_NAME);
	private static final Logger loggerOut = LogManager.getLogger(LOGGER_CHECKOUT_NAME);
	private static final Logger loggerSell = LogManager.getLogger(LOGGER_SELL_NAME);
	
	public static void main(String[] args) { 
		boolean exit = false;		
		//GET User input for Patron
		//TODO enable changing of patrons - the choosing of a patron should probably come after choice of 1,2,3 or 4.
	    StdOut.println("Enter PatronID: ");
		String patronID = StdIn.readLine();
	   
	    // check if patron exist in the list.
		if (dataStore.containsPatron(patronID))
		{
			Patron patron = dataStore.getPatron(patronID);
			StdOut.println("\nSession started for partron: " + patron);
				while (!exit) 
				{
					//TODO make it easier to line up these actions with the ACTION enum.  Dynamically generate this list of options from the enum.
					StdOut.println("Please enter the number next to what you want to do");
					StdOut.println("1 : Check out books to Patron");
					StdOut.println("2 : Check in books from Patron");
					StdOut.println("3 : Sell Book to Patron");
					StdOut.println("4 : View Patron currently checked out books");
					StdOut.println("5 : List books in inventory");
					StdOut.println("6 : List users in system"); 
					StdOut.println("7 : Exit the system"); 
					String in = StdIn.readLine();
					
					ACTION action = ACTION.fromString(in);
					
					if(action==null) {
						StdOut.println("unrecocognized option:" + in);
						continue;
					}
					switch (action) {
					case CheckIn:
						try {
							CheckInController controller = new CheckInController(dataStore);
							controller.startTransaction(patron);
							loggerIn.info("starting transaction for Patron " + patron.getName());
							controller.checkInBooks();
							controller.endTransaction(patron);
						//I am not sure I am happy with the exception handling being done here.  Maybe do it in the controller some how.  
						//These exceptions seems too specific to be handled in the UI.
						//The UI still needs to know that something went wrong and have a general idea on how to handle it.
						} catch (TransactionAlreadyInProgress e) {
							loggerIn.info("transaction already in progress",e);
							StdOut.println("This register is already processing a transaction");
						} catch (NoTransactionInProgress e) {
							loggerIn.info("no transaction in progress",e);
							StdOut.println("cannot add a book, a transaction is not started");
						}  
						break;
					case CheckOut:
						try {
							//using similar method as CheckIn for Controller
							CheckOutController controller = new CheckOutController(dataStore);
							controller.startTransaction(patron);
							loggerIn.info("starting transaction for Patron " + patron.getName());
							controller.checkOutBooks();
							controller.endTransaction(patron);
						} catch (TransactionAlreadyInProgress e) {
							loggerIn.info("transaction already in progress");
							StdOut.println("This register is already processing a transaction");
						} catch (NoTransactionInProgress e) {
							loggerIn.info("no transaction in progress");
							StdOut.println("cannot add a book, a transaction is not started");
						}  
						break;
					case ViewPatron:
						StdOut.println(patron.toString());		
						break;
					case ListBooks:
						loggerIn.info("Listing books from Inventory ");
						//same method as previous
						StdOut.println("\nTODO: list books");
						for (Copy cpy : dataStore.getCopies().values()) {
							StdOut.println(cpy.toString());				
						}
						break;

					case ListUsers:
						loggerIn.info("Listing users in system ");
						//same method as previous
						StdOut.println("\nList users");
						for (Patron pat : dataStore.getPatrons().values()) {
					    	pat.printPatron();
						}
						break;
						
					case SellBook:
						try {
							//Creating new Object Textbook with copyId and price associated.
							//This is loaded along with Patron and Copy for mock data in dataStore
							SellCopyController controller = new SellCopyController(dataStore);
							controller.startTransaction(patron);
							loggerIn.info("starting transaction for Patron " + patron.getName());
							controller.getPrice();
							controller.endTransaction(patron);
						} catch (TransactionAlreadyInProgress e) {
							loggerIn.info("transaction already in progress");
							StdOut.println("This register is already processing a transaction");
						} catch (NoTransactionInProgress e) {
							loggerIn.info("no transaction in progress");
							StdOut.println("cannot add a book, a transaction is not started");
						}  
						break;

					case Exit:
						exit=true;
						break;

					default: // action is unrecognized.
						StdOut.println("unrecocognized option:" + in);
					}
					
				}
			}
		else
		{
			StdOut.println(patronID + " is an invalid Patron");				
		}
	}	
}
