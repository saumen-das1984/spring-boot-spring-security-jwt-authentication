package com.signify.spring.jwt.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Configuration
@PropertySource("classpath:jdbc.properties")
@ConfigurationProperties("usermast")
@Data
@NoArgsConstructor
@ToString
public class UserMastQueryConfig {
	public String select;
	public String selectbyId;
	public String selectbyName;
	public String selectbyEmail;
	public String selectbyAuthCode;
	public String update;
	public String insert;
	public String delete;
}
