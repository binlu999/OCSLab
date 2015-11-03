package com.bbg.lab.observer;

import java.util.Observable;

public class EventGenerator extends Observable implements Runnable {

	
	@Override
	public void run() {
		int index=0;
		while(index<100){
			this.setChanged();
			ChangeEvent changeEvent=new ChangeEvent(index);
			this.notifyObservers(changeEvent);
			index++;
		}
	}

	public static void main(String[] args){
		EventGenerator generator1=new EventGenerator();
		EventGenerator generator2=new EventGenerator();
		EventObserver eventObserver1=new EventObserver("Event Observer 1");
		EventObserver eventObserver2=new EventObserver("Event Observer 2");
		generator1.addObserver(eventObserver1);
		generator2.addObserver(eventObserver1);
		Thread t1=new Thread(generator1);
		Thread t2=new Thread(generator2);
		t1.start();
		t2.start();
	}
}
