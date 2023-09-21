package com.college.GetTheCopy.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.college.GetTheCopy.exception.CustomException;



// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;
	
	private static final Logger logger= LoggerFactory.getLogger( JwtTokenFilter.class);

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		logger.info("Entered Security->  doFilterInternal  Method");
		logger.info("resolveToken  Method called");
		String token = jwtTokenProvider.resolveToken(httpServletRequest);
		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				logger.info("getAuthentication  Method called");
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (CustomException ex) {
			// this is very important, since it guarantees the user is not authenticated at
			// all
			logger.error("CustomException Ocuured "+ex);
			SecurityContextHolder.clearContext();
			httpServletResponse.sendError(401, ex.getMessage());
			logger.info("Exit Security ->  doFilterInternal  Method");
			return;
		}
		logger.info("doFilter  Method called");
		filterChain.doFilter(httpServletRequest, httpServletResponse);
		logger.info("Exit Security ->  doFilterInternal  Method");
	}
	
}
