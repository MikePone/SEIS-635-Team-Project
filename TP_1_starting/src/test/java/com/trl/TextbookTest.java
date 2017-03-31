package com.trl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextbookTest {
	private Textbook textbook;
	private static final String BOOKID="BookID";
	private static final String TITLE="Title";
	private static final String ISBN="ISBN-01";
	private static final String AUTHOR="Author"; 
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTextbook() {
		textbook= new Textbook(BOOKID,40,ISBN,AUTHOR,TITLE);
		assertNotNull(textbook); 
	}

	@Test
	public void testGetBookID() {
		textbook= new Textbook(BOOKID,40,ISBN,AUTHOR,TITLE);
		assertEquals(BOOKID, textbook.getBookID()); 
		try {
			textbook= new Textbook(null,40,ISBN,AUTHOR,TITLE);//should throw an exception
			fail();//fail if we get here,
		}catch (IllegalArgumentException e) {
			//success!
		}catch (Exception e) {
			fail();
		}
	}
  
	@Test
	public void testGetPrice() {
		textbook= new Textbook(BOOKID,40,ISBN,AUTHOR,TITLE);
		assertEquals(40, textbook.getPrice(),.00000000001d);
		try {
			textbook= new Textbook(BOOKID,-1,ISBN,AUTHOR,TITLE);//should throw an exception
			fail();//fail if we get here,
		}catch (IllegalArgumentException e) {
			//success!
		}catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetISBN() {
		textbook= new Textbook(BOOKID,40,ISBN,AUTHOR,TITLE);
		assertEquals(ISBN, textbook.getISBN());
		try {
			textbook= new Textbook(BOOKID,40,null,AUTHOR,TITLE);//should throw an exception
			fail();//fail if we get here,
		}catch (IllegalArgumentException e) {
			//success!
		}catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetAuthor() {
		textbook= new Textbook(BOOKID,40,ISBN,AUTHOR,TITLE);
		assertEquals(AUTHOR, textbook.getAuthor());
		try {
			textbook= new Textbook(BOOKID,40,ISBN,null,TITLE);//should throw an exception
			fail();//fail if we get here,
		}catch (IllegalArgumentException e) {
			//success!
		}catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetTitle() {
		textbook= new Textbook(BOOKID,40,ISBN,AUTHOR,TITLE);
		assertEquals(TITLE, textbook.getTitle());
		try {
			textbook= new Textbook(BOOKID,40,ISBN,AUTHOR,null);//should throw an exception
			fail();//fail if we get here,
		}catch (IllegalArgumentException e) {
			//success!
		}catch (Exception e) {
			fail();
		}
	}

}
