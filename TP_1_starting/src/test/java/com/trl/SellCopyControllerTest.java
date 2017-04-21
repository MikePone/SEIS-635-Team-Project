package com.trl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trl.Hold.HOLD_REASON;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;

public class SellCopyControllerTest 
{
	private SellCopyController sellCopyController;
	private Patron patronTransacted;
	private final static DataStore dataStore = new DataStore();
	private Hold hold;
	
	@Before
	public void setUp() throws Exception 
	{
		sellCopyController = new SellCopyController(dataStore);
		patronTransacted= new Patron("n", "id");
		Copy copy = new Copy("001", new Textbook("id", 1, "ISBN", "author", "title", "edition"));
		hold= new Hold(copy, patronTransacted, HOLD_REASON.OverdueBook);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStartTransaction() throws Exception {
		assertTrue(sellCopyController.startTransaction(patronTransacted) ); 
	}
	 

	@Test(expected=TransactionAlreadyInProgress.class)
	public void testStart2Transactions() throws Exception {
		sellCopyController.startTransaction(patronTransacted);
		sellCopyController.startTransaction(patronTransacted);
		fail();
	}

	@Test
	public void testEndTransaction()  throws Exception{
		assertTrue(sellCopyController.startTransaction(patronTransacted));
		assertTrue(sellCopyController.endTransaction(patronTransacted));
	}

	@Test(expected=NoTransactionInProgress.class)
	public void testEnd2Transaction() throws Exception{
		assertTrue(sellCopyController.startTransaction(patronTransacted));
		assertTrue(sellCopyController.endTransaction(patronTransacted));
		assertTrue(sellCopyController.endTransaction(patronTransacted));
	 
	}
	
//	@Test
//	public void testCalculateAmount()  throws Exception
//	{
//		sellCopyController.startTransaction(patronTransacted);
//		double actualPrice = dataStore.geTextbook("Book1").getPrice();
//		double price = 0 ; 
//		 price = sellCopyController.doSale("Copy1");
//		 
//        assertEquals (actualPrice, price, 100.0);
//	}

}
