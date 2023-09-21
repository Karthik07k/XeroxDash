package com.college.GetTheCopy.security;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("Entered Security -> configure  Method");
		// Disable CSRF (cross site request forgery)
		logger.info("csrf and disable  Method called");
		http.csrf().disable();

		// No session will be created or used by spring security
		logger.info("sessionManagement and sessionCreationPolicy  Method called");
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Entry points
		logger.info("authorizeRequests  Method called");
		http.authorizeRequests()//
				.antMatchers("/api/v1/login").permitAll()
				.antMatchers("/api/v1/register").permitAll().antMatchers("/api/v1/forgotpassword").permitAll()
				.antMatchers("/api/v1/renew").permitAll().antMatchers("/api/v1/files/download/*").permitAll()
				.antMatchers("/h2-console/**/**").permitAll().antMatchers("/api/v1/users").permitAll()
				// Disallow everything else..
				.anyRequest().authenticated();

		// Apply JWT
		logger.info("apply Method called");
		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

		// Optional, if you want to test the API from a browser
		// http.httpBasic();
		logger.info("cors Method called");
		http.cors();
		logger.info("Exit Security -> configure  Method");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		logger.info("Entered Security ->  configure(WebSecurity web) Method");
		// Allow swagger to be accessed without authentication
		web.ignoring().antMatchers("/v2/api-docs")//
				.antMatchers("/swagger-resources/**")//
				.antMatchers("/swagger-ui.html")//
				.antMatchers("/configuration/**")//
				.antMatchers("/webjars/**")//
				.antMatchers("/public")

				// Un-secure H2 Database (for testing purposes, H2 console shouldn't be
				// unprotected in production)
				.and().ignoring().antMatchers("/h2-console/**/**");
		;
		logger.info("Exit Security ->  configure(WebSecurity web) Method");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		logger.info("Entered Security ->  passwordEncoder Method");
		logger.info("Exit Security ->  passwordEncoder Method");
		return new BCryptPasswordEncoder(12);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		logger.info("Entered Security -> authenticationManagerBean Method");
		logger.info("Exit Security ->  authenticationManagerBean Method");
		return super.authenticationManagerBean();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		logger.info("Entered Security -> corsConfigurationSource Method");
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
		configuration.setAllowedHeaders(
				Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		logger.info("Exit Security -> corsConfigurationSource Method");
		return source;
	}

}
