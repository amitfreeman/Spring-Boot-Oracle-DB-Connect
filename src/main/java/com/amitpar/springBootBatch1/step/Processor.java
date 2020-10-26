package com.amitpar.springBootBatch1.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.amitpar.springBootBatch1.dao.SubscriberInfo;
import com.amitpar.springBootBatch1.listener.JobCompletionListener;

public class Processor implements ItemProcessor<SubscriberInfo, SubscriberInfo> {
	
	private static final Logger log = LoggerFactory.getLogger(Processor.class);
	
	public Processor() {
		log.debug("#AMIT Initializing Processor");
		System.out.println("#AMIT Initializing Processor");
	}
    
	@Override
	public SubscriberInfo process(SubscriberInfo data) throws Exception {
		log.debug("#AMIT, in Processor class process method"+data.toString());
		System.out.println("#AMIT, in Processor class process method");
		//data.toString();
		return data;
	}

}