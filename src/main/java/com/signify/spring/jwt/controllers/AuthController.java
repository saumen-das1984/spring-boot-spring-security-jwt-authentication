package com.signify.spring.jwt.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.signify.spring.jwt.auth.response.AuthResponseModel;
import com.signify.spring.jwt.auth.response.GenericResponseCode;
import com.signify.spring.jwt.auth.response.TokenData;
import com.signify.spring.jwt.models.UserDTO;
import com.signify.spring.jwt.payload.request.LoginRequest;
import com.signify.spring.jwt.payload.request.RegisterationRequest;
import com.signify.spring.jwt.payload.response.JwtResponse;
import com.signify.spring.jwt.payload.response.MessageResponse;

import com.signify.spring.jwt.security.jwt.JwtUtils;
import com.signify.spring.jwt.security.services.UserDetailsImpl;
import com.signify.spring.jwt.security.services.UserMastService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserMastService userMastService;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<AuthResponseModel> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserAuthCode(), loginRequest.getUserAuthPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		String refreshToken = jwtUtils.doGenerateRefreshToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		UserDTO userDTO = userMastService.findByUserAuthCode(loginRequest.getUserAuthCode());
		userDTO.setAuthToken(token);
		userDTO.setAuthTokenStatus("ACTIVE");
		userMastService.updateUser(userDTO);
		
		AuthResponseModel authResponseModel = AuthResponseModel.builder()
				.responseCode(GenericResponseCode.SUCCESS.getCode())
				.responseType(GenericResponseCode.SUCCESS.getType())
				.apiResponseMessage("Authentication successful for "+loginRequest.getUserAuthCode())
				.response(TokenData.builder()
						.authorization(token)
						.issuedAt(String.valueOf(jwtUtils.getJwtTokenIssueTime()))
						.expirationAt(String.valueOf(jwtUtils.getJwtTokenExpireTime()))
						.status("ACTIVE")
						.build())
				.status("ACTIVE")
				.build();
		
		return ResponseEntity.ok(authResponseModel);
	}
	
	@PostMapping("/registration")
	public ResponseEntity<AuthResponseModel> registerUser(@Valid @RequestBody RegisterationRequest registerationRequest) {
		if (userMastService.findByUserName(registerationRequest.getUserName()) && userMastService.findByEmail(registerationRequest.getUserEmail())) {
			AuthResponseModel authResponseModel = AuthResponseModel.builder()
					.responseCode(GenericResponseCode.BAD_REQUEST.getCode())
					.responseType(GenericResponseCode.BAD_REQUEST.getType())
					.apiResponseMessage("User Already Exists!!")
					.status("NOT OK")
					.build();
			
			return ResponseEntity.ok(authResponseModel);
		}

		ModelMapper modelMapper = new ModelMapper();
		// user here is a prepopulated User instance
		UserDTO userDTO = modelMapper.map(registerationRequest, UserDTO.class);
		userDTO.setUserAuthPassword(encoder.encode(registerationRequest.getUserAuthPassword()));
		userDTO.setCreateBy(registerationRequest.getUserAuthCode());
		
		userMastService.createUser(userDTO);
		
		AuthResponseModel authResponseModel = AuthResponseModel.builder()
				.responseCode(GenericResponseCode.SUCCESS.getCode())
				.responseType(GenericResponseCode.SUCCESS.getType())
				.apiResponseMessage("Registration successful for "+registerationRequest.getUserAuthCode())
				.status("ACTIVE")
				.build();

		return ResponseEntity.ok(authResponseModel);
	}
}
