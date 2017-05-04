package com.trl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trl.Controller.ACTION;

public class ControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testController() {
		ACTION action = ACTION.fromString("1");
		assertEquals(ACTION.CheckOut, action);
		action = ACTION.fromString("10");
		assertEquals(ACTION.Exit, action);
		action = ACTION.fromString("blah");
		assertNull(action);
	}

}
