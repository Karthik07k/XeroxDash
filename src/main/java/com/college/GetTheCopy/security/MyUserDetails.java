package com.college.GetTheCopy.security;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.college.GetTheCopy.model.User;
import com.college.GetTheCopy.repository.UserRepository;




@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  
  private static final Logger logger= LoggerFactory.getLogger(MyUserDetails.class);

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	 
	  logger.info("Entered Security -> loadUserByUsername  Method");
	  logger.info("findByUserName Method called");
    final User user = userRepository.findByUserName(userName);

    if (user == null) {
    	logger.error("User '" + userName + "' not found");
      throw new UsernameNotFoundException("User '" + userName + "' not found");
    }
    logger.info("Exit Security -> loadUserByUsername  Method");
    return org.springframework.security.core.userdetails.User//
        .withUsername(userName)//
        .password(user.getPassword())//
        .authorities(user.getRoles())//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }
  

}
