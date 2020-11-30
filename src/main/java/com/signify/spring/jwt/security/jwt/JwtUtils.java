package com.signify.spring.jwt.security.jwt;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.signify.spring.jwt.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;

	@Value("${bezkoder.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	private Date jwtTokenIssueTime;
	private Date jwtTokenExpireTime;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		jwtTokenIssueTime = new Date();
		jwtTokenExpireTime = new Date((new Date()).getTime() + jwtExpirationMs);
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(jwtTokenIssueTime)
				.setExpiration(jwtTokenExpireTime)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String removeJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		jwtTokenIssueTime = new Date();
		jwtTokenExpireTime = new Date((new Date()).getTime() - jwtExpirationMs);
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(jwtTokenIssueTime)
				.setExpiration(jwtTokenExpireTime)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
	
	public String doGenerateRefreshToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		Map<String, Object> claims = new HashMap<>();

		Collection<? extends GrantedAuthority> roles = userPrincipal.getAuthorities();
		
		if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			claims.put("isAdmin", true);
		}
		if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			claims.put("isUser", true);
		}
		
		return Jwts.builder().setClaims(claims).setSubject((userPrincipal.getUsername())).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

	}

	public Date getJwtTokenIssueTime() {
		return jwtTokenIssueTime;
	}

	public void setJwtTokenIssueTime(Date jwtTokenIssueTime) {
		this.jwtTokenIssueTime = jwtTokenIssueTime;
	}

	public Date getJwtTokenExpireTime() {
		return jwtTokenExpireTime;
	}

	public void setJwtTokenExpireTime(Date jwtTokenExpireTime) {
		this.jwtTokenExpireTime = jwtTokenExpireTime;
	}
}
