package com.amitpar.springBootBatch1.dao;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.amitpar.springBootBatch1.controller.JobInvokerController;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;


@Configuration
public class OracleConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(JobInvokerController.class);
	
	 @Value("${spring.datasource.username}")
    private String username;
	 
	 @Value("${spring.datasource.password}")
    private String password;
	 
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    
    @Value("${oracle.ucp.minPoolSize}")
    private String minPoolSize;

    @Value("${oracle.ucp.maxPoolSize}")
    private String maxPoolSize;
    

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public void setMinPoolSize(String minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public void setMaxPoolSize(String maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    

    @Bean(name="Zandu")
    @Primary
    public DataSource getDataSource() {
        PoolDataSource pds = null;
        System.out.println("#AMIT Initializing DataSource");
        System.out.println("#AMIT Oracle ULR="+url);
        log.debug("#AMIT Initializing DataSource");
        log.debug("#AMIT Oracle ULR="+url);
        try {
            pds = PoolDataSourceFactory.getPoolDataSource();

            pds.setUser(username);
            pds.setPassword(password);
            pds.setURL(url);	
            pds.setConnectionFactoryClassName(driverClassName);
            pds.setMinPoolSize(Integer.valueOf(minPoolSize));
            pds.setInitialPoolSize(10);
            pds.setMaxPoolSize(Integer.valueOf(maxPoolSize));

        } catch (SQLException ea) {
        	log.error("Error connecting to the database: " + ea.getMessage());
            System.err.println("Error connecting to the database: " + ea.getMessage());
        }

        return pds;
    }
    
   /* @Bean(name = "templateZandu")
    public JdbcTemplate getJdbcTemplate(@Qualifier("Zandu") DataSource oracleDataSource) {
    	System.out.println("#AMIT Initializing JdbcTemplate");
    	return new JdbcTemplate(oracleDataSource); 
    }*/
}