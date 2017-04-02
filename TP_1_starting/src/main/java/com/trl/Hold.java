package com.trl;

import java.util.Date;

public class Hold {
	//The copy for this hold.  This can be null if the hold is the result of unpaid fines.
	private final Copy holdCopy;
	//the patron who now has this hold on their account
	private final Patron holdPatron;
	//The date the hold was created
	private final Date holdDate;
	private final HOLD_REASON reason;
	
	public enum HOLD_REASON {
		OverdueBook,DamagedBook,UnpaidFine;
	}
	
	public Hold(Copy copy, Patron patron, HOLD_REASON reason){
		this.holdCopy=copy;
		if (patron == null ) {
			throw new IllegalArgumentException("Patron cannot be null");
		}
		this.holdPatron=patron;
		
		if (reason == null){
			throw new IllegalArgumentException("hold REason cannot be null");
		}
		this.reason=reason;
		
		if (!reason.equals(HOLD_REASON.UnpaidFine) && (copy ==null)) {
			throw new IllegalArgumentException();
		}
		this.holdDate=new Date();
	}
}
