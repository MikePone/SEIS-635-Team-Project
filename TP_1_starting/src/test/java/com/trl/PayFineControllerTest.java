package com.trl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trl.exception.HasHoldsException;
import com.trl.exception.NoTransactionInProgress;
import com.trl.exception.TransactionAlreadyInProgress;

public class PayFineControllerTest {
	private PayFineController controller;
	private final static DataStore dataStore = new DataStore();
	private Patron patronTransacted;

	@Before
	public void setUp() throws Exception {
		controller = new PayFineController(dataStore);
		patronTransacted= new Patron("n", "id");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStartTransaction() throws Exception{
		assertTrue(controller.startTransaction(patronTransacted) ); 
	}

	@Test(expected=TransactionAlreadyInProgress.class)
	public void testStart2Transactions() throws Exception {
		controller.startTransaction(patronTransacted);
		controller.startTransaction(patronTransacted);
		fail();
	}

	@Test
	public void testEndTransaction()  throws Exception{
		assertTrue(controller.startTransaction(patronTransacted));
		assertTrue(controller.endTransaction(patronTransacted));
	}

	@Test(expected=NoTransactionInProgress.class)
	public void testEnd2Transaction() throws Exception{
		assertTrue(controller.startTransaction(patronTransacted));
		assertTrue(controller.endTransaction(patronTransacted));
		assertTrue(controller.endTransaction(patronTransacted));
	 
	}
	 
	@Test
	public void testPayFine() {
	//	fail("Not yet implemented");
	}

}
