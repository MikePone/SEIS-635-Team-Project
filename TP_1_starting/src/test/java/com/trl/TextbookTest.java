package com.trl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextbookTest {
	private Textbook textbook;
	private static final String BOOKID="BookID";
	private static final String TITLE="Title";
	private static final String ISBN="ISBN-01";
	private static final String AUTHOR="Author"; 
	private static final String EDITION="Edition1"; 
	
	@Before
	public void setUp() throws Exception {
		textbook= new Textbook(BOOKID,new BigDecimal("40"),ISBN,AUTHOR,TITLE, EDITION); 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTextbook() { 
		assertNotNull(textbook); 
	}

	@Test
	public void testGetBookID() {
		assertEquals(BOOKID, textbook.getBookID());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetNullBookId() {  
		textbook= new Textbook(null,new BigDecimal("40"),ISBN,AUTHOR,TITLE, EDITION);//should throw an exception
	}
	
	@Test
	public void testGetPrice() { 
		assertEquals(new BigDecimal("40"), textbook.getPrice());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetNegativePrice() {  
		textbook= new Textbook(BOOKID,new BigDecimal("-1"),ISBN,AUTHOR,TITLE, EDITION);//should throw an exception
	}
	
	@Test
	public void testGetISBN() {
		assertEquals(ISBN, textbook.getISBN()); 
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetNullISBN() {  
		textbook= new Textbook(BOOKID,new BigDecimal("40"),null,AUTHOR,TITLE, EDITION);//should throw an exception 
	}

	@Test
	public void testGetAuthor() {
		assertEquals(AUTHOR, textbook.getAuthor()); 
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetNullAuthor() { 
		textbook= new Textbook(BOOKID,new BigDecimal("40"),ISBN,null,TITLE, EDITION);//should throw an exception
	}

	@Test
	public void testGetTitle() {
		assertEquals(TITLE, textbook.getTitle());
		try {
			textbook= new Textbook(BOOKID,new BigDecimal("40"),ISBN,AUTHOR,null, EDITION);//should throw an exception
			fail();//fail if we get here,
		}catch (IllegalArgumentException e) {
			//success!
		}catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetEdition() {
		assertEquals(EDITION, textbook.getEdition()); 
		textbook= new Textbook(BOOKID,new BigDecimal("40"),ISBN,AUTHOR,TITLE, null);//this is OK.
		assertEquals(null, textbook.getEdition()); 
		
	}

}
