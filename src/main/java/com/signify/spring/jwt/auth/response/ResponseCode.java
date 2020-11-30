package com.signify.spring.jwt.auth.response;

/**
 * @author Saumen
 *
 */
public interface ResponseCode {

	String getDescription();

	String getCode();
	
	String getType();
}
