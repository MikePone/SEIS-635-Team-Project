package com.trl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trl.Hold.HOLD_REASON;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;

public class ManageHoldControllerTest {
	private ManageHoldController controller;
	private final static DataStore dataStore = new DataStore(); 
	private final Patron patronHolds = dataStore.getPatron("008"); 
	private Hold hold;
	private RentalAppViewStub view;
	
	@Before
	public void setUp() throws Exception {
		view = new RentalAppViewStub();
		controller = new ManageHoldController(dataStore,view); 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStartTransaction() throws Exception{
		assertTrue(controller.startTransaction(patronHolds) ); 
	}

	@Test(expected=TransactionAlreadyInProgress.class)
	public void testStart2Transactions() throws Exception {
		controller.startTransaction(patronHolds);
		controller.startTransaction(patronHolds);
		fail();
	}

	@Test
	public void testEndTransaction()  throws Exception{
		assertTrue(controller.startTransaction(patronHolds));
		assertTrue(controller.endTransaction(patronHolds));
	}

	@Test(expected=NoTransactionInProgress.class)
	public void testEnd2Transaction() throws Exception{
		assertTrue(controller.startTransaction(patronHolds));
		assertTrue(controller.endTransaction(patronHolds));
		assertTrue(controller.endTransaction(patronHolds));
	 
	}

	@Test
	public void testRemoveHold() throws Exception{
		view.addInputString("1");
		view.addInputString("3");
		assertTrue(controller.startTransaction(patronHolds));
		controller.manageHold();
		assertTrue(controller.endTransaction(patronHolds));
	}
	@Test
	public void testAddHold() throws Exception{
		view.addInputString("2");
		view.addInputString("3");
		assertTrue(controller.startTransaction(patronHolds));
		controller.manageHold();
		assertTrue(controller.endTransaction(patronHolds));
	}

}
