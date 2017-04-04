package com.trl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CopyTest {
	private Copy copy;
	private Patron patron;
	private Textbook textBook;
	private static final String COPYID="cid";

	@Before
	public void setUp()
	{
		patron= new Patron("Patron1", "001");
		textBook = new Textbook("id", 40, "ISBN", "author", "title");
		copy = new Copy(COPYID, textBook);		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCopy() {
		assertNotNull(copy); 
	}

	@Test
	public void testGetCopyID() {
		assertEquals(COPYID, copy.getCopyID()); 
		try {
			copy= new Copy(null, textBook);//should throw an exception
			fail();//fail if we get here,
		}catch (IllegalArgumentException e) {
			//success!
		}catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetOutTo() 
	{
		Patron newPatron = new Patron("Patron1", "001");
		assertEquals(newPatron, patron); 
	}
	
	@Test
	public void testGetTextbook() 
	{
		Textbook newTextbook = new Textbook("id", 40, "ISBN", "author", "title");
		assertEquals(newTextbook, textBook); 
	}
	
	
	
	

}
