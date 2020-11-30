package com.signify.spring.jwt.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	@NotBlank
	private String userAuthCode;

	@NotBlank
	private String userAuthPassword;
	
	@NotBlank
	private String authType;

}
