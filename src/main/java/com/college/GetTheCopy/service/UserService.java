package com.college.GetTheCopy.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.college.GetTheCopy.exception.AuthenticationException;
import com.college.GetTheCopy.model.Role;
import com.college.GetTheCopy.model.User;
import com.college.GetTheCopy.repository.UserRepository;
import com.college.GetTheCopy.security.JwtTokenProvider;
import com.college.GetTheCopy.security.TokenProvider;
import com.college.GetTheCopy.utility.EmailUtility;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private EmailUtility emailUtility;

	@Autowired
	private Environment env;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public TokenProvider signin(String email, String password) {
		logger.info("Entered Service -> signin  Method");
		try {
			logger.info("authenticate Method called");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			logger.info("findByUserName Method called with userName " + email);
			User user = userRepository.findByUserName(email);
			logger.info("createToken Method called with userName " + email);
			TokenProvider tp = jwtTokenProvider.createToken(email, user.getId(), user.getRoles());
			logger.info("Exit Service -> signin  Method");
			return tp;
		} catch (AuthenticationException e) {
			logger.error("AuthenticationException Occured: " + e);
			logger.info("Exit Service -> signin  Method");
			throw new AuthenticationException("Invalid userName/password supplied");
		}
	}
	
	public TokenProvider renew(String refreshToken) {
		logger.info("Entered Service -> renew  Method");
		try {
			logger.info("refreshToken Method called ");
			TokenProvider tp = jwtTokenProvider.refreshToken(refreshToken);
			logger.info("Exit Service -> renew  Method");	
			return tp;

		} catch (AuthenticationException e) {
			logger.error("AuthenticationException Occured: "+e);
			logger.info("Exit Service -> signin  Method");
			System.out.println("Authentication Exception " + e);
			throw new AuthenticationException("Invalid userName/password supplied");
		}
	}
	
	public boolean forgotpassword(String emailId) {
		logger.info("Entered Service -> forgotpassword  Method");
		if (userRepository.existsByUserName(emailId)) {
			logger.info("generatePasswordAndMail Method called ");
			generatePasswordAndMail(emailId);
		} else {
			throw new AuthenticationException("Invalid Email");
		}
		logger.info("Exit Service -> forgotpassword  Method");
		return true;
	}

	
	public void generatePasswordAndMail(String emailId) {
		logger.info("Entered Service ->  generatePasswordAndMail Method");
		if (env.getProperty("mail.sent").equals("true")) {
			String subject = "SPSI: Temporary Password Generated";
			String password = getRandomNumberString();
			String body = "Dear Sir/Madam, \n\nThe new password for the \"" + emailId + "\" is \"" + password
					+ "\"\n\nRegards,\nKarthik\nGetTheCopy";
			logger.debug("updatePassword Method called with emailId "+ emailId+" password "+password);	
			updatePassword(emailId, password);
			logger.info("sendMailTest  Method called emailId "+emailId+" subject "+subject+" body "+body);	
			emailUtility.sendMailTest(emailId, subject, body);

		}

	}
	public boolean updatePassword(String emailId, String newPassword) {
		logger.info("Entered Service -> updatePassword Method");
		try {
			logger.debug("findByEmailId Method called with emailId "+emailId);
			User user = userRepository.findByUserName(emailId);
			user.setPassword(passwordEncoder.encode(newPassword));
			logger.info("save Method called");	
			userRepository.save(user);
			logger.info("Exit Service -> updatePassword Method");
			return true;
		} catch (Exception e) {
			logger.error("Exception Occured: "+e);
			logger.info("Exit Service -> updatePassword Method");
			return false;
		}

	}
	
	public boolean updatePasswordValues(String emailId, String newPassword) {
		logger.info("Entered Service -> updatePasswordValues Method");
		try {
			logger.debug("findByEmailId Method called with emailId "+emailId);
			User user = userRepository.findByUserName(emailId);
			user.setPassword(passwordEncoder.encode(newPassword));
			logger.info("save Method called");
			userRepository.save(user);
			logger.info("Exit Service -> updatePasswordValues Method");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception Occured: "+e);
			logger.info("Exit Service -> updatePasswordValues Method");
			return false;
		}

	}
	
	public String getRandomNumberString() {
		logger.info("Entered Service ->  getRandomNumberString Method");
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		logger.info("Exit Service ->  getRandomNumberString Method");
		return String.format("%06d", number);
	}
	
	public User createUser(User user) {
		logger.info("Entered Service -> createUser  Method");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		logger.info("save Method called");
		logger.info("Exit Service -> createOfficer  Method");
		return userRepository.save(user);
	}

	public User fetchUserByUserName(String email) {
		logger.info("Entered Service -> fetchUserByEmail Method");
		logger.info("fetchUserByEmail Method called");
		logger.info("Exit Service ->  fetchUserByEmail Method");
		return userRepository.findByUserName(email);
	}
	
	public Iterable<User> fetchUsers() {
		return userRepository.findAll();
	}
	
	public List<User> fetchUserByRole(String roleName) {
		logger.info("Entered Service -> fetchUserByRole Method");
		logger.info("findAllByRoles Method called");
		logger.info("Exit Service -> fetchUserByRole Method");
		
		return userRepository.findAllByRoles(Role.valueOf(roleName));
	}
	public User fetchUser(String userId) {
		logger.info("Entered Service -> fetchUser Method");
		logger.info("findById Method called with userId "+ userId);
		Optional<User> user = userRepository.findById(userId);
		logger.info("Exit Service -> fetchUser Method");
		return user.get();
	}

	public boolean isUserExistById(String id) {
		return userRepository.existsById(id);
	}

}
