package com.bbg.myontimenotice.data.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class EmailMessage {
	
	public EmailMessage(){
		super();
	}
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
    private String name;

	@Persistent
	private Blob messageBlob;

	@Persistent
	private String fromAddress;

	
	public Key getKey() {
		return key;
	}


	public void setKey(Key key) {
		this.key = key;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Blob getMessageBlob() {
		return messageBlob;
	}


	public void setMessageBlob(Blob blob) {
		this.messageBlob=blob;
	}


	public String getKeyString() {
		if(this.key!=null)
			return KeyFactory.keyToString(this.key);
		return null;
	}


	public void setFromAddress(String from) {
		this.fromAddress=from;
	}


	public String getFromAddress() {
		return fromAddress;
	}
	
	

}
