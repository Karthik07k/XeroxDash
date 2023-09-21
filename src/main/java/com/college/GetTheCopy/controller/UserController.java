package com.college.GetTheCopy.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.college.GetTheCopy.common.dto.CommonResponse;
import com.college.GetTheCopy.common.dto.OauthTokenRequest;
import com.college.GetTheCopy.common.dto.ResetPasswordDTO;
import com.college.GetTheCopy.dto.UserRequestDTO;
import com.college.GetTheCopy.dto.UserResponseDTO;
import com.college.GetTheCopy.dto.Student.UserStudentRequestDTO;
import com.college.GetTheCopy.exception.AuthenticationException;
import com.college.GetTheCopy.exception.BadRequestException;
import com.college.GetTheCopy.exception.ConflictException;
import com.college.GetTheCopy.exception.NotActivatedException;
import com.college.GetTheCopy.exception.NotFoundException;
import com.college.GetTheCopy.model.GeneralStatusEnum;
import com.college.GetTheCopy.model.Student;
import com.college.GetTheCopy.model.User;
import com.college.GetTheCopy.model.XeroxShop;
import com.college.GetTheCopy.security.JwtTokenProvider;
import com.college.GetTheCopy.security.TokenProvider;
import com.college.GetTheCopy.service.ShopService;
import com.college.GetTheCopy.service.StudentService;
import com.college.GetTheCopy.service.UserService;

@RestController
@RequestMapping("api/v1")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	ShopService shopService;

	@Autowired
	StudentService studentService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@CrossOrigin
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestHeader("Authorization") String basicAuth,
			@RequestBody UserRequestDTO userRequest) throws IOException {
		logger.info("Entered CommonController-> generateToken  Method");
		try {

			if (userRequest.getEmailId() == null) {
				logger.error("EmailId is Mandatory");
				throw new BadRequestException("EmailId is Mandatory");
			}

			String authorization = basicAuth;
			String[] values = null;

			if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
				// Authorization: Basic base64credentials
				String base64Credentials = authorization.substring("Basic".length()).trim();
				byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
				String credentials = new String(credDecoded, StandardCharsets.UTF_8);
				// credentials = username:password
				values = credentials.split(":", 2);
				if (values[0].equals("sso-client") && values[1].equals("secret-key")) {

					if (userRequest.getRoles().get(0).toString() == "ROLE_STUDENT"
							&& userRequest.getStudent() != null) {
						studentService.createStudent(getStudentDetail(userRequest));

					} else if (userRequest.getRoles().get(0).toString() == "ROLE_SHOP"
							&& userRequest.getShop() != null) {
						shopService.createShop(getShop(userRequest));

					} else if (userRequest.getRoles().get(0).toString() == "ROLE_CUSTOMER"
							|| userRequest.getRoles().get(0).toString() == "ROLE_ADMIN") {
						userService.createUser(getUser(userRequest));

					} else {
						logger.error("Invalid Role Found");
						throw new BadRequestException("Invalid Role Found");
					}

					UserResponseDTO userResponseDTO = new UserResponseDTO();
					userResponseDTO.setResponse("Created Successfully");
					return new ResponseEntity<UserResponseDTO>(userResponseDTO, HttpStatus.CREATED);

				} else {
					logger.error("Token is not valid");
					throw new AuthenticationException("Token is not valid");
				}
			} else {
				logger.error("Token is not valid");
				logger.info("Exit CommonController-> generateToken  Method");
				throw new AuthenticationException("Token is not valid");
			}

		} catch (DataIntegrityViolationException e) {
			logger.error("DataIntegrityViolationException Occured: " + e);
			logger.info("Exit  WebController  -> createOfficer    Method");
			throw new ConflictException("Email already exist");
		} catch (BadRequestException e) {
			logger.error("BadRequestException Occured: " + e);
			logger.info("Exit  WebController  -> createOfficer    Method");
			throw e;
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
			logger.info("Exit  WebController  -> createOfficer    Method");
			throw e;
		}
	}

	@CrossOrigin
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestHeader("Authorization") String basicAuth,@RequestBody OauthTokenRequest oauthRequest)
			throws IOException {
		logger.info("Entered CommonController-> generateToken  Method");
		try {
			User user = userService.fetchUserByUserName(oauthRequest.getEmail());

			if (user == null) {
				throw new NoSuchElementException("User doesnot exist");
			}

			if (user.getRoles().get(0).toString() == "ROLE_SHOP") {
				XeroxShop shop = shopService.fetchShopByUserName(oauthRequest.getEmail());
				if (shop.getStatus() != GeneralStatusEnum.ACCEPTED) {
					throw new NotActivatedException("Shop is not activated yet");
				}

			}

			String authorization = basicAuth;
			String[] values = null;

			if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
				// Authorization: Basic base64credentials
				String base64Credentials = authorization.substring("Basic".length()).trim();
				byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
				String credentials = new String(credDecoded, StandardCharsets.UTF_8);
				// credentials = username:password
				values = credentials.split(":", 2);
				if (values[0].equals("sso-client") && values[1].equals("secret-key")) {
					TokenProvider tp = userService.signin(oauthRequest.getEmail(), oauthRequest.getPassword());
					logger.info("Exit CommonController-> generateToken  Method");
					return new ResponseEntity<TokenProvider>(tp, HttpStatus.OK);
				} else {
					logger.error("Token is not valid");
					throw new AuthenticationException("Token is not valid");
				}
			} else {
				logger.error("Token is not valid");
				logger.info("Exit CommonController-> generateToken  Method");
				throw new AuthenticationException("Token is not valid");
			}

		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException Occured: " + e);
			logger.info("Exit CommonController-> generateToken  Method");
			throw new NotFoundException("User not found on the given ID");
		} catch (NotActivatedException e) {
			logger.error("NotActivatedException Occured: " + e);
			logger.info("Exit CommonController-> generateToken  Method");
			throw new NotActivatedException(
					"Your shop is not in active state, please contact the manager of GetTheyCopy");
		} catch (AuthenticationException e) {
			logger.error("AuthenticationException Occured: " + e);
			logger.info("Exit CommonController-> generateToken  Method");
			throw e;
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
			logger.info("Exit CommonController-> generateToken  Method");
			throw e;
		}
	}

	@GetMapping(value = "/renew")
	public ResponseEntity<?> generateTokenFromRefresh(@RequestHeader("Authorization") String refreshToken)
			throws IOException {
		logger.info("Entered CommonController-> generateTokenFromRefresh  Method");

		try {

			if (refreshToken != null && refreshToken.toLowerCase().startsWith("bearer")) {
				String token = refreshToken.split(" ")[1];
				TokenProvider tp = userService.renew(token);
				logger.info("Exit CommonController-> generateTokenFromRefresh  Method");
				return new ResponseEntity<TokenProvider>(tp, HttpStatus.OK);
			} else {
				logger.error("Not a valid User");
				throw new AuthenticationException("Not a valid User");
			}
		} catch (AuthenticationException e) {
			logger.error("AuthenticationException Occured: " + e);
			logger.info("Exit CommonController-> generateTokenFromRefresh  Method");
			throw e;
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
			logger.info("Exit CommonController-> generateTokenFromRefresh  Method");
			throw e;
		}

	}

	@PostMapping(value = "/forgotpassword")
	public ResponseEntity<?> forgotPassword(@RequestParam("emailid") String emailid) throws IOException {
		logger.info("Entered CommonController-> forgotPassword  Method");
		try {
			logger.info("Check emailid exist or not, if exists forgotpassword Method called");
			if (userService.forgotpassword(emailid)) {
				CommonResponse sm = new CommonResponse(true, "Email Sent Successfully");
				logger.info("Exit CommonController-> forgotPassword  Method");
				return new ResponseEntity<CommonResponse>(sm, HttpStatus.OK);
			} else {

				logger.error("Invalid Email");
				throw new AuthenticationException("Invalid Email");
			}
		} catch (AuthenticationException e) {
			logger.error("AuthenticationException Occured: " + e);
			logger.info("Exit CommonController-> forgotPassword  Method");
			throw e;
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
			logger.info("Exit CommonController-> forgotPassword  Method");
			throw e;
		}
	}

	@PostMapping(value = "/updatepassword")
	public ResponseEntity<?> updatePassword(@RequestBody ResetPasswordDTO request,
			@RequestHeader(name = "Authorization") String token) throws IOException {

		logger.info("Entered CommonController-> updatePassword  Method");
		token = token.replace("Bearer", "");
		String username = jwtTokenProvider.getUsername(token);

		try {
			if (userService.updatePasswordValues(username, request.getPassword())) {
				CommonResponse sm = new CommonResponse(true, "Updated Successfully");

				logger.info("Exit CommonController-> updatePassword  Method");
				return new ResponseEntity<CommonResponse>(sm, HttpStatus.OK);
			} else {
				logger.error("Invalid Email");
				throw new AuthenticationException("Invalid Email");
			}
		} catch (AuthenticationException e) {
			logger.error("AuthenticationException Occured: " + e);
			logger.info("Exit CommonController-> updatePassword  Method");
			throw e;
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
			logger.info("Exit CommonController-> updatePassword  Method");
			throw e;
		}
	}

	@GetMapping(value = "/users")
	public ResponseEntity<?> fetchUsers() throws IOException {
		logger.info("Entered CommonController-> fetchUsers  Method");

		try {
			logger.info("fetchUserByUserName Method called");
			Iterable<User> users = userService.fetchUsers();
//			User user = userService.fetchUserByRole(null);
			logger.info("Exit CommonController-> fetchUsers  Method");
			return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
			logger.info("Exit CommonController-> fetchUsers  Method");
			throw e;
		}

	}

	// Conversions DTO - Entity starts here
	public User getUser(UserRequestDTO userRequestDTO) {
		logger.info("Entered  Controller -> getUser Method");

		User user = new User();
		user.setAddress(userRequestDTO.getAddress());
		user.setUserName(userRequestDTO.getEmailId());
		user.setMobileNumber(userRequestDTO.getMobileNumber());
		user.setName(userRequestDTO.getName());
		user.setPassword(userRequestDTO.getPassword());
		user.setRoles(userRequestDTO.getRoles());
		logger.info("Exit  WebController -> getUser Method");
		return user;
	}

	// Conversions DTO - Entity starts here
	public Student getStudentDetail(UserRequestDTO userRequestDTO) {
		logger.info("Entered  Controller -> getStudent Method");
		Student student = new Student();

		student.setAddress(userRequestDTO.getAddress());
		student.setUserName(userRequestDTO.getEmailId());
		student.setMobileNumber(userRequestDTO.getMobileNumber());
		student.setName(userRequestDTO.getName());
		student.setPassword(userRequestDTO.getPassword());
		student.setRoles(userRequestDTO.getRoles());

		student.setDegree(userRequestDTO.getStudent().getDegree());
		student.setSem(userRequestDTO.getStudent().getSem());
		student.setCollege(userRequestDTO.getStudent().getCollege());

		Long count = studentService.getStudentCount() + 1;
		student.setStudentId("Student" + "_" + count);

		logger.info("Exit  WebController -> getUser Method");
		return student;
	}

	// Conversions DTO - Entity starts here
	public XeroxShop getShop(UserRequestDTO userRequestDTO) {

		XeroxShop xeroxShop = new XeroxShop();

		xeroxShop.setAddress(userRequestDTO.getAddress());
		xeroxShop.setUserName(userRequestDTO.getEmailId());
		xeroxShop.setMobileNumber(userRequestDTO.getMobileNumber());
		xeroxShop.setName(userRequestDTO.getName());
		xeroxShop.setPassword(userRequestDTO.getPassword());
		xeroxShop.setRoles(userRequestDTO.getRoles());

		xeroxShop.setShopName(userRequestDTO.getShop().getShopName());
		xeroxShop.setShopAddress(userRequestDTO.getShop().getShopAddress());
		xeroxShop.setStartFrom(userRequestDTO.getShop().getStartFrom());
		xeroxShop.setEndAt(userRequestDTO.getShop().getEndAt());
		xeroxShop.setCostPerPage(userRequestDTO.getShop().getCostPerPage());
		xeroxShop.setIsShopOpen(userRequestDTO.getShop().getIsShopOpen());
		xeroxShop.setMetersAway(userRequestDTO.getShop().getMetersAway());
		Long count = shopService.getShopCount() + 1;
		xeroxShop.setShopId("Shop" + "_" + count);
		xeroxShop.setStatus(GeneralStatusEnum.REQUESTED);

		return xeroxShop;
	}
//	public User getCustomer(UserRequestDTO userRequestDTO) {
//		logger.info("Entered  Controller -> getCustomer Method");
//		User user = new User();
//		if(user != "ADMIN") {
//		user.setAddress(userRequestDTO.getAddress());
//		user.setUserName(userRequestDTO.getEmailId());
//		user.setMobileNumber(userRequestDTO.getMobileNumber());
//		user.setName(userRequestDTO.getName());
//		user.setPassword(userRequestDTO.getPassword());
//		user.setRoles(userRequestDTO.getRoles());
//		
//
//		student.setDegree(userRequestDTO.getStudent().getDegree());
//		student.setSem(userRequestDTO.getStudent().getSem());
//		student.setCollege(userRequestDTO.getStudent().getCollege());
//		logger.info("Exit  WebController -> getUser Method");
//		return student;
//	}
//	}
}
