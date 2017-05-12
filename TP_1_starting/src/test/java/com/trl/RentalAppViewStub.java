package com.trl;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class RentalAppViewStub implements RentalAppView {

	private final Queue<String> inputs = new ArrayBlockingQueue<String>(10);
	private final List<String> outputs = new ArrayList<String>();
	
	public void addInputString(String msg){
		inputs.add(msg);
	}
	
	public List<String> getOutputs(){
		return outputs;
	}
	
	@Override
	public void showMessage(Message msg) {
		this.outputs.add(msg.getTheMessage());
	}
	
	@Override
	public String showMessageWithInput(Message msg) {
		this.outputs.add(msg.getTheMessage()); 
		return inputs.poll();
	};
	
	@Override
	public String toString() {

		String retVal = "";
		for (String string : outputs) {
			retVal+=string;
		}
		
		return retVal;
	}
	
}
