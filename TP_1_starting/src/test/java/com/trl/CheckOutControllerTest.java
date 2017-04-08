package com.trl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trl.Hold.HOLD_REASON;
import com.trl.exception.HasHoldsException;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;

public class CheckOutControllerTest 
{
	private CheckOutController checkOutController;
	private Patron patronTransacted;
	private Hold hold;
	private final static DataStore dataStore = new DataStore();

	
	@Before
	public void setUp() throws Exception 
	{
		checkOutController = new CheckOutController(dataStore);
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
			patronTransacted.addHold(hold);
			returnResult = checkOutController.startTransaction(patronTransacted);
		} 
		catch (HasHoldsException e) {
			//should hit here, success
		} catch (TransactionAlreadyInProgress e) {
			//not testing
		}
		
		try 
		{
			returnResult = checkOutController.startTransaction(null);
		} 
		catch (HasHoldsException e) {
			//not testing
		} catch (TransactionAlreadyInProgress e) {
			//should hit here, success
		}
	}
	
	@Test
	public void testEndTransaction()  
	{
		boolean result = true;
		boolean returnResult = startTranscation(patronTransacted);
		try 
		{
			returnResult = checkOutController.endTransaction(patronTransacted);
		} 
		catch (NoTransactionInProgress e) {
			//not expected
		}
		assertEquals(result,returnResult ); 
	}
	
	public boolean startTranscation(Patron patron)
	{
		boolean returnResult = false;
		try 
		{
			returnResult = checkOutController.startTransaction(patronTransacted);
		} 
		catch (TransactionAlreadyInProgress e) 
		{
			//not expected
		} catch (HasHoldsException e) {
			//not expected
		}
		
		return returnResult;
	}

}
