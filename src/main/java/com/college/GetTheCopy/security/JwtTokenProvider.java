package com.college.GetTheCopy.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.college.GetTheCopy.exception.CustomException;
import com.college.GetTheCopy.model.Role;
import com.college.GetTheCopy.model.User;
import com.college.GetTheCopy.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	/**
	 * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key
	 * here. Ideally, in a microservices environment, this key would be kept on a
	 * config-server.
	 */

	private String secretKey = "secret-key";

	@Autowired
	UserRepository userRepository;

	@Autowired
	Environment env;

	private long validityInMilliseconds; // 1h

	private long refreshValidityInMilliseconds;

	@Autowired
	private MyUserDetails myUserDetails;

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@PostConstruct
	protected void init() {
		logger.info("Entered Security -> init  Method");
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		logger.info("Exit Security -> init  Method");
	}

	public TokenProvider createToken(String userName, String officerId, List<Role> roles) {
		logger.info("Entered Security -> createToken  Method");
		Claims claims = Jwts.claims().setSubject(userName);
		claims.put("userId", officerId);
		claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority()))
				.filter(Objects::nonNull).collect(Collectors.toList()));

		Date now = new Date();

		validityInMilliseconds = (Long.parseLong(env.getProperty("access.token.time")) * 60 * 1000);
		refreshValidityInMilliseconds = (Long.parseLong(env.getProperty("refresh.token.time")) * 60 * 1000);

		Date validity = new Date(now.getTime() + validityInMilliseconds);
		Date refreshValidity = new Date(now.getTime() + refreshValidityInMilliseconds);

		String accessToken = Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();

		String refreshToken = Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(refreshValidity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();

		TokenProvider tokenProvider = new TokenProvider();
		tokenProvider.setAccessToken(accessToken);
		tokenProvider.setRefreshToken(refreshToken);
		logger.info("Exit Security -> createToken  Method");
		return tokenProvider;

	}

	public TokenProvider refreshToken(String refreshToken) {
		logger.info("Entered Security -> refreshToken  Method");
		try {

			String userName = getUsername(refreshToken);
			logger.info("findByUserName  Method called");
			User user = userRepository.findByUserName(userName);
			logger.info("Exit Security -> refreshToken  Method");
			return createToken(userName, user.getId(), user.getRoles());
		} catch (Exception e) {
			logger.error("Exception Occured " + e);
			logger.info("Exit Security -> refreshToken  Method");

			throw new CustomException(e.getMessage());
		}

	}

	public Authentication getAuthentication(String token) {
		logger.info("Entered Security -> getAuthentication  Method");
		logger.info("loadUserByUsername  Method called");
		UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
		logger.info("Exit Security -> getAuthentication  Method");
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		logger.info("Entered Security ->getUsername  Method");
		logger.info("Exit Security ->getUsername  Method");
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

	}

	public String resolveToken(HttpServletRequest req) {
		logger.info("Entered Security -> resolveToken Method");
		logger.info("getHeader Method called");
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			logger.info("Exit Security -> resolveToken Method");
			return bearerToken.substring(7);
		}
		logger.info("Exit Security -> resolveToken Method");
		return null;
	}

	public boolean validateToken(String token) {
		logger.info("Entered Security -> validateToken Method");
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			logger.info("Exit Security -> validateToken Method");
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			logger.error("JwtException | IllegalArgumentException Occured " + e);
			logger.info("Exit Security -> validateToken Method");
			throw new CustomException("Expired or invalid JWT token");
		}
	}

}
