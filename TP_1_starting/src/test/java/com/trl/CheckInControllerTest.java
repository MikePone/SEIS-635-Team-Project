package com.trl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trl.Hold.HOLD_REASON;
import com.trl.exception.HasHoldsException;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;

public class CheckInControllerTest 
{
	private CheckInController checkInController;
	private Patron patronTransacted;
	private final static DataStore dataStore = new DataStore();
	private Hold hold;
	
	@Before
	public void setUp() throws Exception 
	{
		checkInController = new CheckInController(dataStore);
		patronTransacted= new Patron("n", "id");
		Copy copy = new Copy("001", new Textbook("id", 1, "ISBN", "author", "title", "edition"));
		hold= new Hold(copy, patronTransacted, HOLD_REASON.OverdueBook);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStartTransaction()  
	{
		boolean result = true;
		boolean returnResult = startTranscation(patronTransacted);
		assertEquals(result,returnResult ); 
		
		try 
		{
			returnResult = checkInController.startTransaction(null);
		} 
		catch (TransactionAlreadyInProgress e) {
			//should hit here, success
		}
	}
	
	public boolean startTranscation(Patron patron)
	{
		boolean returnResult = false;
		try 
		{
			returnResult = checkInController.startTransaction(patronTransacted);
		} 
		catch (TransactionAlreadyInProgress e) 
		{
			//not expected
		}
		
		return returnResult;
	}
	
	@Test
	public void testEndTransaction()  
	{
		boolean result = true;
		boolean returnResult = startTranscation(patronTransacted);
		try 
		{
			returnResult = checkInController.endTransaction(patronTransacted);
		} 
		catch (NoTransactionInProgress e) {
			//not expected
		}
		assertEquals(result,returnResult ); 
	}

}
