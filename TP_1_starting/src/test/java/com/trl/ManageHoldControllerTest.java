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
	private Patron patronTransacted;
	private Hold hold;
	
	@Before
	public void setUp() throws Exception {
		controller = new ManageHoldController(dataStore);
		patronTransacted= new Patron("n", "id");
		Copy copy = new Copy("001", new Textbook("id", new BigDecimal("1"), "ISBN", "author", "title", "edition"));
		hold= new Hold(copy, patronTransacted, HOLD_REASON.OverdueBook);
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
	public void testManageHold() {
	//	fail("Not yet implemented");
	}

}
