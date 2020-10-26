package com.amitpar.springBootBatch1.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import com.amitpar.springBootBatch1.controller.JobInvokerController;

public class JobCompletionListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionListener.class);
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		
		System.out.println("#AMIT IN END Job Status = "+jobExecution.getStatus().toString());
		log.debug("#AMIT IN END Job Status = "+jobExecution.getStatus().toString());
		
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("BATCH JOB COMPLETED SUCCESSFUL");
			System.out.println("BATCH JOB COMPLETED SUCCESSFULLY");
		}
	}

}