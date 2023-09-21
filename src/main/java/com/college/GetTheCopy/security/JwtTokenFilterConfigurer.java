package com.college.GetTheCopy.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private JwtTokenProvider jwtTokenProvider;
  
  private static final Logger logger= LoggerFactory.getLogger(JwtTokenFilterConfigurer.class);

  public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
		logger.info("Entered Security ->  configure Method");
    JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
    logger.info("addFilterBefore Method called");
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	logger.info("Exit Security ->  configure Method");

  }

}
