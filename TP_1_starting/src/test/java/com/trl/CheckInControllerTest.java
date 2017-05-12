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
	private final Patron patronHolds = ds.getPatron("008"); 
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
	
	@Test
	public void testCheckInBadCopy() throws Exception {
		view.addInputString("CopyA");
		view.addInputString("N");
		assertTrue(controller.startTransaction(patron)); 
		controller.checkInBooks();
		assertTrue(controller.endTransaction(patron)); 
		assertEquals(3, view.getOutputs().size());
		assertEquals("copyID CopyA not found!", view.getOutputs().get(1));
	}

	@Test
	public void testCheckIn2Books() throws Exception {
		view.addInputString("Copy1");
		view.addInputString("Y");
		view.addInputString("Copy2");
		view.addInputString("N");
		assertTrue(controller.startTransaction(patron)); 
		controller.checkInBooks();
		assertTrue(controller.endTransaction(patron)); 
		assertEquals(6, view.getOutputs().size());
		assertEquals("CopyID Copy1 not checked out to Patron!", view.getOutputs().get(1));
		assertEquals("CopyID Copy2 not checked out to Patron!", view.getOutputs().get(4));
	}
	
	@Test(expected=NoTransactionInProgress.class)
	public void testCheckInNoTransaction() throws Exception { 
		controller.checkInBooks(); 
	}

	@Test
	public void testCheckInWithHolds() throws Exception { 
		view.addInputString("Copy1");
		view.addInputString("N");
		assertTrue(controller.startTransaction(patronHolds)); 
		controller.checkInBooks();
		assertTrue(controller.endTransaction(patronHolds)); 
		assertEquals(4, view.getOutputs().size());
		System.out.println(view);
		assertTrue(view.getOutputs().get(0).startsWith("-------******----------\nALERT : THIS PATRON HAS HOLD, OUTSTANDING DUES : "));
	}
}
