package com.bbg.lab.observer;

public class ChangeEvent {

	private int index;

	public ChangeEvent(int index) {
		this.index=index;
	}

	@Override
	public String toString() {
		return "My index is "+index+";";
	}

}
