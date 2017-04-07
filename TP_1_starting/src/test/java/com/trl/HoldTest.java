package com.trl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trl.Hold.HOLD_REASON;

public class HoldTest {
	private Hold hold;
	private Patron patron;
	private Copy copy;
	
	@Before
	public void setUp() throws Exception 
	{
		Copy copy = new Copy("001", new Textbook("id", 1, "ISBN", "author", "title", "edition"));
		patron = new Patron("n", "id");
		hold= new Hold(copy, patron, HOLD_REASON.OverdueBook);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHold() {
		assertNotNull(hold); 
		try {
			hold= new Hold(null, patron, HOLD_REASON.OverdueBook);//should throw an exception
			fail();//fail if we get here,
		}catch (IllegalArgumentException e) {
			//success!
		}catch (Exception e) {
			fail();
		}
		try {
			hold= new Hold(copy, null, HOLD_REASON.OverdueBook);//should throw an exception
			fail();//fail if we get here,
		}catch (IllegalArgumentException e) {
			//success!
		}catch (Exception e) {
			fail();
		}
		try {
			hold= new Hold(copy, patron, null);//should throw an exception
			fail();//fail if we get here,
		}catch (IllegalArgumentException e) {
			//success!
		}catch (Exception e) {
			fail();
		}
	}

}
