package com.trl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PatronTest {
	private Patron patron;
	private static final String NAME="Patron1";
	private static final String PATRONID="001";
	private ArrayList<Copy> copiesOut= new ArrayList<Copy>();
	private ArrayList<Hold> patronHolds; 
	private Copy newCopy;
	
	@Before
	public void setUp()
	{
		patron= new Patron(NAME, PATRONID);

		Textbook newTextbook = new Textbook("id", 40, "ISBN", "author", "title");
		newCopy = new Copy("cid", newTextbook);
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPatron() {
		assertNotNull(patron); 
	}

	@Test
	public void testGetName() {
		assertEquals(NAME, patron.getName()); 
		try {
			patron= new Patron(null,PATRONID);//should throw an exception
			fail();//fail if we get here,
		}catch (IllegalArgumentException e) {
			//success!
		}catch (Exception e) {
			fail();
		}
	}

}
