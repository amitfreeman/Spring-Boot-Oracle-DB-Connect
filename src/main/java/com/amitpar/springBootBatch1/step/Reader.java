package com.amitpar.springBootBatch1.step;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.amitpar.springBootBatch1.dao.SubscriberInfo;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

class subscriberRowMapper implements RowMapper<SubscriberInfo>{

	public static final String SUBSCRIBER = "SUBSCRIBER_NO";
	public static final String BAN = "CUSTOMER_ID";

	@Override
	public SubscriberInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		SubscriberInfo subI=new SubscriberInfo();

		subI.setBan(rs.getLong(BAN));
		subI.setSubscriber(rs.getString(SUBSCRIBER));
		return subI;
	}

}



public class Reader implements ItemReader<SubscriberInfo>  {

	  private static final String QUERY_FIND_SUBSCRIBER =
			  "select  customer_id, subscriber_no from subscriber " +
			  " where sys_creation_date > to_date('20200101','YYYYMMDD') "+
		" and rownum < 11";
	 
	  private static final Logger log = LoggerFactory.getLogger(Reader.class);
	  
	@Autowired
	@Qualifier("Zandu")
	DataSource dataSourceVar;
	
	JdbcTemplate jdbcTemplate=null;
	List<SubscriberInfo> subscriberInfoList=null;
	
	private int count;
	  
	/*@Autowired
	@Qualifier("templateZandu")
	JdbcTemplate jdbcTemplate;  	*/
	
	public Reader() {
		log.info("#AMIT Initializing Reader class");
		System.out.println("#AMIT Initializing Reader class");
		count = 0;
	}
	

	@Override
	public SubscriberInfo read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		jdbcTemplate=new JdbcTemplate(dataSourceVar);
		subscriberInfoList = jdbcTemplate.query(QUERY_FIND_SUBSCRIBER,
                          new subscriberRowMapper());     
		
		log.info("#AMIT IN most important read method ***********");
		System.out.println("#AMIT IN most important read method *********** ");
	  
		if(count<subscriberInfoList.size()) {
			log.debug("Reading count="+count);
	    	System.out.println("Reading count="+count);
	    	return subscriberInfoList.get(count++);
	    }
	    else {
	    	count=0;
	    }
	    
		return null;
	}
 
}