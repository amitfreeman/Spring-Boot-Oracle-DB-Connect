package com.amitpar.springBootBatch1.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.RowMapper;

import com.amitpar.springBootBatch1.SpringBootBatchDemo1Application;
//import com.amitpar.springBootBatch1.JobCompletionListener;
import com.amitpar.springBootBatch1.dao.SubscriberInfo;
import com.amitpar.springBootBatch1.listener.JobCompletionListener;
import com.amitpar.springBootBatch1.step.Processor;
import com.amitpar.springBootBatch1.step.Reader;
import com.amitpar.springBootBatch1.step.Writer;

/* for method 2 itemReader */
/*class subscriberRowMapper implements RowMapper<SubscriberInfo>{

    public static final String SUBSCRIBER = "SUBSCRIBER_NO";
    public static final String BAN = "CUSTOMER_ID";
	
	@Override
	public SubscriberInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
	    SubscriberInfo subI=new SubscriberInfo();
	    
	    subI.setBan(rs.getLong(BAN));
	    subI.setSubscriber(rs.getString(SUBSCRIBER));
		return subI;
	}
}*/

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	
    /*@Autowired
	@Qualifier("Zandu")
	public DataSource dataSource;*/
	  
	/*@Autowired
	@Qualifier("templateZandu")
	public JdbcTemplate jdbcTemplate;*/

	
	/* *************
	 * Reader, processor, Writer beans 
	 * **************/
	/* Method1 different class file for reader */
	@Bean
	public Reader reader() {
		  System.out.println("#AMIT in Batch config create bean reader");
		  log.debug("#AMIT in Batch config create bean reader");
	      return new Reader();
	}
	
	/* Method2 same file implementation for JDBC reader */
	/*@Bean
	public JdbcCursorItemReader<SubscriberInfo> itemReader() {
		System.out.println("#AMIT_2 creating jdbc itemwriter bean");
		
		return new JdbcCursorItemReaderBuilder<SubscriberInfo>()
				.dataSource(dataSource)
				.sql(" select  customer_id, subscriber_no from subscriber  where sys_creation_date > to_date('20200101','YYYYMMDD')  and rownum < 11")
				.rowMapper(new subscriberRowMapper())
				.name("subscriberReader")
				.build();
	}*/
	
	@Bean
	public Processor processor() {
		  System.out.println("#AMIT in Batch config create bean processor");
		  log.debug("#AMIT in Batch config create bean processor");
	      return new Processor();
	}
	
	@Bean
	public Writer writer() {
		  System.out.println("#AMIT in Batch config create bean writer");
		  log.debug("#AMIT in Batch config create bean writer");
	      return new Writer();
	}
	
	@Bean
	public Job processJob(Step orderStep1) {
		System.out.println("#AMIT, in processJob bean");
		log.debug("#AMIT, in processJob bean");
		//System.out.println("DS= "+dataSource.toString()+" , "+jdbcTemplate.toString());
		return jobBuilderFactory.get("processJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(orderStep1)
				.end()
				.build();
	}

	@Bean
	public Step orderStep1() {
		System.out.println("#AMIT, in orderStep1");
		log.debug("#AMIT, in orderStep1");
				//+ "="+dataSource.toString()+" , "+jdbcTemplate.toString());
		return stepBuilderFactory.get("orderStep1").<SubscriberInfo, SubscriberInfo> chunk(5)
				.reader( reader() )   /* change name reader/itemReader as needed */
				.processor( processor() )
				.writer( writer() )
				.build();
	}

	@Bean
	public JobExecutionListener listener() {
		System.out.println("#AMIT in listner");
		log.debug("#AMIT in listner");
		return new JobCompletionListener();
	}
	
	/*@Bean
	public Job runJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		System.out.println("#AMIT in runJob");


		return this.jobBuilderFactory.get("runJob")
				.start(orderStep1())
				.build();
	}*/
	
    //@Scheduled(cron = "0 *//*1 * * * ?")
    /*public void perform() throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job, params);
    }*/
	
}