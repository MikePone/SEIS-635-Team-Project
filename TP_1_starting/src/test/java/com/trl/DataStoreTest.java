package com.trl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataStoreTest {
	private DataStore dataStore;
	String patronId = "001";
	private Patron patron;
	private Copy copy;

	
	@Before
	public void setUp()
	{
		dataStore= new DataStore();
		patron = new Patron("Patron1", "001");
		Textbook book = new Textbook("Title1", 100, "123","Author","Title1");
		copy = new Copy("Copy1", book);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCopy() {
		assertNotNull(dataStore); 
	}

	@Test
	public void testContainsPatron() 
	{
		boolean result = true;
		boolean returnResult = dataStore.containsPatron(patronId);
		assertEquals(result,returnResult ); 
	}
	
	@Test
	public void testGetPatron() 
	{
		Patron newPatron = dataStore.getPatron(patronId);
		assertEquals(patron,newPatron ); 
	}
	
	@Test
	public void testContainsCopy() 
	{
		boolean result = true;
		boolean returnResult = dataStore.containsCopy("Copy1");
		assertEquals(result,returnResult ); 
	}
	
	@Test
	public void testGetCopy() 
	{
		Copy newCopy = dataStore.getCopy("Copy1");
		assertEquals(copy,newCopy ); 
	}

}
