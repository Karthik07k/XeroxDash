package com.college.GetTheCopy.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.college.GetTheCopy.model.Role;
import com.college.GetTheCopy.model.User;
import com.college.GetTheCopy.repository.UserRepository;



@Configuration
public class InitialDataConfiguration {
	private static final Logger logger= LoggerFactory.getLogger(InitialDataConfiguration.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostConstruct
	public void postConstruct() {
		logger.info("Entered Config-> postConstruct  Method");
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ROLE_ADMIN);
		
		User user = new User();
		user.setAddress("Bangalore");
		user.setUserName("admin@gmail.com");
		user.setMobileNumber("7793241234");
		user.setName("Karthik");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setRoles(roles);

		logger.info("findByEmailId Method called");
		logger.debug("findByEmailId Method called with ID: admin@gmail.com");
		User user1 = userRepository.findByUserName("admin@gmail.com");
		if (user1 == null) {
			userRepository.save(user);
			logger.info("Exit Config-> postConstruct  Method");
		}
	}

}