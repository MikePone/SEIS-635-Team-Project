package com.trl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trl.Hold.HOLD_REASON;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;

public class CheckInControllerTest {
	private CheckInController controller;
	private final DataStore ds = new DataStore();
	private final Patron patron = ds.getPatron("001");
	private Hold hold;
	
	@Before
	public void setUp() throws Exception {
		controller = new CheckInController(ds);
		Copy copy = new Copy("001", new Textbook("id", 1, "ISBN", "author", "title", "edition"));
		hold= new Hold(copy, patron, HOLD_REASON.OverdueBook);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStartTransaction() throws Exception{
		assertTrue(controller.startTransaction(patron)); 
	}

	@Test
	public void testEndTransaction() throws Exception{
		assertTrue(controller.startTransaction(patron)); 
		assertTrue(controller.endTransaction(patron)); 
	}
	
	@Test(expected=TransactionAlreadyInProgress.class)
	public void testStart2Transactions() throws Exception{
		assertTrue(controller.startTransaction(patron)); 
		assertTrue(controller.startTransaction(patron)); 
	}
	@Test(expected=NoTransactionInProgress.class)
	public void testEndNoTransactions() throws Exception{
		assertTrue(controller.endTransaction(patron));
	}

}
