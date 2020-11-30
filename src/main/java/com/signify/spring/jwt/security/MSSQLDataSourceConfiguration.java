package com.signify.spring.jwt.security;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * 
 * @author saumend
 * @since 2020-04-18
 */


@Configuration
public class MSSQLDataSourceConfiguration {
	
	@Autowired
	private Environment environment;

	@Bean(destroyMethod = "")
	@Qualifier("mySQLDataSource")
	public DataSource msSQLExtranetDataSourceLocal() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(environment.getProperty("spring.datasource.url"));
		dataSource.setUsername(environment.getProperty("spring.datasource.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.password"));
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		return dataSource;
	}
	
}
