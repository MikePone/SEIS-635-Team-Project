package com.trl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

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
	private final DataStore dataStore = new DataStore();
	private Hold hold;
	private RentalAppViewStub view;

	@Before
	public void setUp() throws Exception 
	{
		view = new RentalAppViewStub();
		sellCopyController = new SellCopyController(dataStore, view);
		patronTransacted= new Patron("n", "id");
		Copy copy = new Copy("001", new Textbook("id", new BigDecimal("1"), "ISBN", "author", "title", "edition"));
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
	
	@Test
	public void testCalculateAmount()  throws Exception
	{
		view.addInputString("Copy1");
		view.addInputString("N");
		assertTrue(sellCopyController.startTransaction(patronTransacted)); 
		sellCopyController.doSale();
		assertTrue(sellCopyController.endTransaction(patronTransacted)); 
		assertEquals("The book Copy1 is sold to n for the amount of 100", view.getOutputs().get(1));
	}

}
