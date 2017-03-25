package com.trl;
  
public class ActionController {
	
	public enum ACTION {
		CheckOut("1"), 
		CheckIn("2"), 
		SellBook("3"),
		ViewPatron("4"),
		ListBooks("5"), 
		ListUsers("6"),
		Exit("7");
		
		private String value;
		ACTION(String val) {
			this.value=val;
		}
		
		public static ACTION fromString(String text) {
		    for (ACTION b : ACTION.values()) {
		      if (b.value.equalsIgnoreCase(text)) {
		        return b;
		      }
		    }
		    return null;
		  }
	} 
	
}
