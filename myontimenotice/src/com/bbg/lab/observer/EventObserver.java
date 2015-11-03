package com.bbg.lab.observer;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class EventObserver implements Observer {

	private String name;

	public EventObserver(String name) {
		this.name=name;
	}

	@Override
	public void update(Observable caller, Object event) {
		try {
			int slep = getRandomInt(0,1000);
			System.out.println(name+" thread "+Thread.currentThread().getId()+" sleep "+slep);
			Thread.sleep(slep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String callerName = caller.getClass().getName();
		String eventKind = event.getClass().getName();
		System.out.println(name+" get event from "+callerName+", evnet kind of "+eventKind+", event is "+event);
	}
	
	private static int getRandomInt(int min,int max){
		Random ran=new Random();
		return ran.nextInt((max-min)+1)+min;
	}
	
	public static void main(String[] args){
		for(int index=0;index<100;index++){
			System.out.println(getRandomInt(0,1000));
		}
	}

}
