package com.amitpar.springBootBatch1;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class SpringBootBatchDemo1Application {
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootBatchDemo1Application.class);
	
	public static void main(String[] args) {
		System.out.println("#AMIT beginning, in SpringBootBatchDemo1Application");
		log.debug("#AMIT beginning, in SpringBootBatchDemo1Application");
		
		SpringApplication.run(SpringBootBatchDemo1Application.class, args);
		
		/*System.out.println("#AMIT, RUN MAN RUN");
		System.exit(SpringApplication.exit(
				SpringApplication.run(SpringBootBatchDemo1Application.class, args) ));*/
	}

}
