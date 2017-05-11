package com.trl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CheckInControllerTest.class, CheckOutControllerTest.class, ControllerTest.class, CopyTest.class, 
	DataStoreTest.class, HoldTest.class, ManageHoldControllerTest.class, PatronTest.class, PayFineControllerTest.class, 
	SellCopyControllerTest.class, TextbookTest.class, 
		  })
public class AllTests {

}
