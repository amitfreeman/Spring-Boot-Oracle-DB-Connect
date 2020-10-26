package com.amitpar.springBootBatch1.step;
import java.io.File;
import java.io.FileWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import com.amitpar.springBootBatch1.dao.SubscriberInfo;

@Scope("step")
public class Writer implements ItemWriter<SubscriberInfo> {
	
	String fileName=null;
	private static final Logger log = LoggerFactory.getLogger(Writer.class);
	
	public Writer() {
		System.out.println("#AMIT Initializing Writer");;
	}
	
	/* Get job parameters */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
	    JobParameters jobParameters = stepExecution.getJobParameters();
	    
	    String jobName=jobParameters.getString("JobName");
	    String jobRec=jobParameters.getString("JobRec");
	    long dateTime=jobParameters.getLong("DateTime");
	    Date runDate=new Date(dateTime);
	    DateFormat  dateFormatter =new SimpleDateFormat("yyyyMMdd_HHmmss");
	    /*System.out.println("#AMIT in writer setting jobName="+jobName+", jobRec="+jobRec+
	    		", Date="+dateFormatter.format(runDate));*/
	    
	    fileName=jobName+"_"+jobRec+"_"+dateFormatter.format(runDate)+".csv";
	}

	@Override
	public void write(List<? extends SubscriberInfo> items) throws Exception {
		
		/*String fileName="SampleFile_"+dtf.format(now).toString()+".csv";*/
		log.debug("#AMIT in writer, openeing file <"+fileName+">"+" in append mode");
		System.out.println("#AMIT in writer, openeing file <"+fileName+">"+" in append mode");
		File myFile = new File(fileName);
		FileWriter myWriter = new FileWriter(myFile, true);
		
		for (SubscriberInfo sub : items) {
			log.debug("Writing the data " + sub.toString());
			
			System.out.println("Writing the data " + sub.toString());				
			myWriter.write(Long.toString(sub.getBan())+","+sub.getSubscriber());
			myWriter.write("\n");
		}
		myWriter.close();
	}

}