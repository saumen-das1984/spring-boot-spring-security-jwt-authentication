package com.signify.spring.jwt.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @author saumend
 * @since 2020-04-18
 */

@Configuration
public class DBTemplateConfiguration {

	@Bean
	@Qualifier("jdbcTemplate")
	public JdbcTemplate msSQLMonitorJdbcTemplate(@Qualifier("mySQLDataSource")DataSource mySQLDataSource) {
		return new JdbcTemplate(mySQLDataSource);
	}
}