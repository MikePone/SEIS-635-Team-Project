package com.trl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextbookTest {
	private Textbook textbook;
	private static final String COPYID="CopyID";
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTextbook() {
		textbook= new Textbook(COPYID,40);
		assertNotNull(textbook); 
	}

	@Test
	public void testGetCopyID() {
		textbook= new Textbook(COPYID,40);
		assertEquals(COPYID, textbook.getCopyID());
		textbook= new Textbook(null,40);
		//TODO this should throw an exception...
		assertEquals(null, textbook.getCopyID());
	}
  
	@Test
	public void testGetPrice() {
		textbook= new Textbook(COPYID,40);
		assertEquals(40, textbook.getPrice(),.00000000001d);
		textbook= new Textbook(COPYID,-1);
		//TODO this should throw an exception.
		assertEquals(-1, textbook.getPrice(),.00000000001d);
	}

}
