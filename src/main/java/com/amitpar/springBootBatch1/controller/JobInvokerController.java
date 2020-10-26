package com.amitpar.springBootBatch1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.amitpar.springBootBatch1.config.BatchConfig;
 
@RestController
//@Controller
public class JobInvokerController {
 
	private static final Logger log = LoggerFactory.getLogger(JobInvokerController.class);
	
    @Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    Job processJob;
    
   /* @RequestMapping("/index.html")
	public  ModelAndView firstPage() {
		return new ModelAndView("index");
	}*/
    
    @RequestMapping("/invokejob") 
    public String handle() throws Exception {
    	System.out.println("#AMIT Initializing JobInvokerController");
    	log.debug("#AMIT Initializing JobInvokerController");
         //JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
               //.toJobParameters();
    
    	JobParameters jobParameters = new JobParametersBuilder()
    			.addLong("DateTime", System.currentTimeMillis())
                .addString("JobName", "CSTESTJOB")
                .addString("JobRec", "DUMMY")
                .toJobParameters();
         jobLauncher.run(processJob, jobParameters);
 
        return "Batch job has been invoked";
         //return new ModelAndView("invoked");
    }
}