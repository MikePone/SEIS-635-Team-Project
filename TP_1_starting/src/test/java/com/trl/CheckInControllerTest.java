package com.trl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;

public class CheckInControllerTest {
	private CheckInController controller;
	private final DataStore ds = new DataStore();
	private final Patron patron = ds.getPatron("001"); 
	private RentalAppViewStub view;
	
	@Before
	public void setUp() throws Exception {
		view = new RentalAppViewStub();
		controller = new CheckInController(ds,view);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStartTransaction() throws Exception{
		assertTrue(controller.startTransaction(patron)); 
	}

	/*@Test
	public void testMoreBooks() throws Exception{
		boolean result = true;
		controller.checkInBooks();
		assertEquals(result,returnResult ); 
	}*/
	
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
	
	@Test
	public void testCheckIn() throws Exception {
		view.addInputString("Copy1");
		view.addInputString("N");
		assertTrue(controller.startTransaction(patron)); 
		controller.checkInBooks();
		assertTrue(controller.endTransaction(patron)); 
		assertEquals(3, view.getOutputs().size());
		assertEquals("CopyID Copy1 not checked out to Patron!", view.getOutputs().get(1));
	}
}
