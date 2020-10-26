package com.amitpar.springBootBatch1.dao;

public class SubscriberInfo {
    long ban;
    String subscriber;
    
	public long getBan() {
		return ban;
	}
	public void setBan(long ban) {
		this.ban = ban;
	}
	public String getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}
    
    @Override
    public String toString() {
		return "[BAN="+this.ban+", subscriber="+this.subscriber+"]";
    	
    }
}
