package com.trl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trl.Controller.ACTION;
import com.trl.exception.HasHoldsException;
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
	
	public static void main(String[] args) 
	{
		StdOut.println("-------******----------"); 
		StdOut.println("Running Code tips : ");
		StdOut.println("10 Mock Patron Ids format; 001 through 010 ");
		StdOut.println("10 Mock Copy Ids format; Copy1 through Copy10 ");
		StdOut.println("10 Mock Textbook loaded with respective price ");
		StdOut.println("3 Mock Patron Ids with HOLD : 008, 009 and 010 ");
		StdOut.println("3 Mock Copy Ids with due dates[set to yesterday; day prior to sysdate] : Copy08, Copy09, Copy10 ");
		StdOut.println("-------******----------"); 
		StdOut.println("-------******----------"); 
		StdOut.println("To start any transaction or function, a valid Patron Id is required : ");
		StdOut.println("List of operation are displayed upon entering Patron Id");
		StdOut.println("-------******----------"); 
		StdOut.println(); 
		boolean exit = false;		
		//GET User input for Patron
		//TODO enable changing of patrons - the choosing of a patron should probably come after choice of 1,2,3 or 4.
	    StdOut.println("Enter PatronID: ");
		String patronID = StdIn.readLine();
	   
	    // check if patron exist in the list.
		if (dataStore.containsPatron(patronID))
		{
			Patron patron = dataStore.getPatron(patronID); // create patron 
			
			StdOut.println("\nSession started for partron: " + patron);
			//Looping through patron session unless exit
				while (!exit) 
				{
					StdOut.println("Please enter the number next to what you want to do");
					StdOut.println("1 : Check out books to Patron");
					StdOut.println("2 : Check in books from Patron");
					StdOut.println("3 : Sell Book to Patron");
					StdOut.println("4 : View Patron currently checked out books");
					StdOut.println("5 : List books in inventory");
					StdOut.println("6 : List users in system"); 
					StdOut.println("7 : Pay Fine"); 
					StdOut.println("8 : Run Hold check (Admin Only)"); 
					StdOut.println("9 : Manage Holds (Admin Only"); 
					StdOut.println("10 : Exit the system"); 
					
					//Check if patron has hold and Alert the staff about dues
					if (patron.hasHolds())
					{
						loggerIn.info("ALERT : THIS PATRON HAS HOLD, OUTSTANDING DUES " + patron);
						StdOut.println("-------******----------"); 
						StdOut.println("ALERT : THIS PATRON HAS HOLD, OUTSTANDING DUES : " + patron);
						StdOut.println("-------******----------"); 
					}
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
							controller.startTransaction(patron); // start patron transaction session
							loggerIn.info("starting transaction for Patron " + patron.getName());
							controller.checkInBooks();
							controller.endTransaction(patron);// end patron transaction session
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
							controller.startTransaction(patron);// start patron transaction session
							loggerIn.info("starting transaction for Patron " + patron.getName());
							controller.checkOutBooks();
							controller.endTransaction(patron);// end patron transaction session
						} catch (TransactionAlreadyInProgress e) {
							loggerIn.info("transaction already in progress");
							StdOut.println("This register is already processing a transaction");
						} catch (NoTransactionInProgress e) {
							loggerIn.info("no transaction in progress");
							StdOut.println("cannot add a book, a transaction is not started");
						}  catch (HasHoldsException e){
							loggerIn.info("Patron has holds on account, cannot checkout books.");
							StdOut.println("Patron has holds on account, cannot checkout books.");
						}
						break;
					case ViewPatron:
						//print out Patron details
						StdOut.println(patron.toString());		
						break;
					case ListBooks:
						loggerIn.info("Listing books from Inventory ");
						StdOut.println("\nTODO: list books");
						//print out all the copy list stored in the dataStore mock up.
						for (Copy cpy : dataStore.getCopyList()) {
							StdOut.println(cpy.toString());				
						}
						break;

					case ListUsers:
						loggerIn.info("Listing users in system ");
						StdOut.println("\nList users");
						//print out all the Patron list stored in the dataStore mock up.
						for (Patron pat : dataStore.getPatronList()) {
					    	pat.printPatron();
						}
						break;
						
					case SellBook:
						try {
							//Creating new Object Textbook with copyId and price associated.
							//This is loaded along with Patron and Copy for mock data in dataStore
							SellCopyController controller = new SellCopyController(dataStore);
							controller.startTransaction(patron);// start patron transaction session
							loggerIn.info("starting transaction for Patron " + patron.getName());
 							controller.doSale();
							//TODO -display to customer price and handle payment?
							controller.endTransaction(patron);// end patron transaction session
						} catch (TransactionAlreadyInProgress e) {
							loggerIn.info("transaction already in progress");
							StdOut.println("This register is already processing a transaction");
						} catch (NoTransactionInProgress e) {
							loggerIn.info("no transaction in progress");
							StdOut.println("cannot add a book, a transaction is not started");
						}  
						break;
						
					case PayFine:
						try {
							PayFineController controller = new PayFineController(dataStore);
							controller.startTransaction(patron); // start patron transaction session
							loggerIn.info("starting transaction for Patron " + patron.getName());
 							controller.payFine();
							controller.endTransaction(patron);// end patron transaction session
						} catch (TransactionAlreadyInProgress e) {
							loggerIn.info("transaction already in progress");
							StdOut.println("This register is already processing a transaction");
						} catch (NoTransactionInProgress e) {
							loggerIn.info("no transaction in progress");
							StdOut.println("cannot pay fine, a transaction is not started");
						}  
						break;
					case RunHoldCheck:
						/*
						 * We are going to go through all the copies and make sure their due dates are not BEFORE the current date and thus overdue.
						 */
						if (patron.hasHolds())
							{
							loggerIn.info("There are outstanding holds for the partron " + patron);
							StdOut.println("There are outstanding holds for the partron");
							StdOut.println( patron);
							}
						else
						{
							loggerIn.info("There are NO holds for the partron " + patron);
							StdOut.println("There are NO holds for the partron");
							StdOut.println( patron);							
						}
						break;
					case ManageHolds:
						try {
							ManageHoldController controller = new ManageHoldController(dataStore);
							controller.startTransaction(patron);// end patron transaction session
							loggerIn.info("starting transaction for Patron " + patron.getName());
 							controller.manageHold();
							controller.endTransaction(patron);// end patron transaction session
						} catch (TransactionAlreadyInProgress e) {
							loggerIn.info("transaction already in progress");
							StdOut.println("This register is already processing a transaction");
						} catch (NoTransactionInProgress e) {
							loggerIn.info("no transaction in progress");
							StdOut.println("cannot pay fine, a transaction is not started");
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
