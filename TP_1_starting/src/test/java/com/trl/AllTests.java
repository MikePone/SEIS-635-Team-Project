package com.trl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CheckInControllerTest.class, CheckOutControllerTest.class, CopyTest.class, DataStoreTest.class,
		HoldTest.class, PatronTest.class, SellCopyControllerTest.class, TextbookTest.class })
public class AllTests {

}
