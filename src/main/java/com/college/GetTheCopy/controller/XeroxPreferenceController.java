package com.college.GetTheCopy.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.college.GetTheCopy.dto.xeroxpreferences.XeroxPreferenceRequestDTO;
import com.college.GetTheCopy.exception.NotFoundException;
import com.college.GetTheCopy.model.FileDetail;
import com.college.GetTheCopy.model.PaperSideEnum;
import com.college.GetTheCopy.model.PaperSizeEnum;
import com.college.GetTheCopy.model.TransactionStatusEnum;
import com.college.GetTheCopy.model.XeroxPreference;
import com.college.GetTheCopy.service.FileDetailService;
import com.college.GetTheCopy.service.ShopService;
import com.college.GetTheCopy.service.UserService;
import com.college.GetTheCopy.service.XeroxPreferenceService;

@RestController
@RequestMapping("api/v1")
public class XeroxPreferenceController {
	private static final Logger logger = LoggerFactory.getLogger(XeroxPreferenceController.class);

	@Autowired
	UserService userService;

	@Autowired
	ShopService shopService;

	@Autowired
	FileDetailService fileDetailService;

	@Autowired
	XeroxPreferenceService xeroxPreferenceService;

	@CrossOrigin
	@PostMapping("/xeroxPreferences")
	public ResponseEntity<?> createXeroxPreferences(@RequestBody XeroxPreferenceRequestDTO requestDTO)
			throws IOException {

		try {
			// 1. Is user available
			if (!userService.isUserExistById(requestDTO.getUserId())) {
				logger.error("User not found on the given ID");
				throw new NotFoundException("User not found on the given ID");
			}

			// 2. Is Document available
			if (!fileDetailService.isFileDetailExistById(requestDTO.getFileDetailID())) {
				logger.error("File not found on the given ID");
				throw new NotFoundException("File not found on the given ID");
			}

			// 3. Is shop available
			if (!shopService.isXeroxShopById(requestDTO.getShopId())) {
				logger.error("Shop not found on the given ID");
				throw new NotFoundException("Shop not found on the given ID");
			}

			// 1. Create XeroxPrefernce object using Mapper

			XeroxPreference request = convertToXeroxPreference(requestDTO);
			// 2. Store in the Database via XeroxpreferencesService ->
			// XeroxPreferencesRepository
			XeroxPreference response = xeroxPreferenceService.createXeroxPreference(request);

			// 3. Give XeroxPreferenceResponseDTO to the client

			return new ResponseEntity<XeroxPreference>(response, HttpStatus.CREATED);

		} catch (NotFoundException e) {
			logger.error("NotFoundException Occured: " + e);
			logger.info("Exit ShopController -> statusUpdate  Method");
			throw e;
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
			logger.info("Exit ShopController -> statusUpdate  Method");
			throw e;
		}

	}

	private XeroxPreference convertToXeroxPreference(XeroxPreferenceRequestDTO requestDTO) {

		XeroxPreference request = new XeroxPreference();
		request.setCopy(requestDTO.getCopy());
		FileDetail fileDetail = fileDetailService.fetchFileDetailByID(requestDTO.getFileDetailID());
		request.setFileDetail(fileDetail);
		request.setUser(userService.fetchUser(requestDTO.getUserId()));
		request.setXeroxShop(shopService.fetchShopById(requestDTO.getShopId()));
		request.setIsColor(requestDTO.getIsColor());
		request.setSide(requestDTO.getSide());
		request.setSize(requestDTO.getSize());
		request.setPageCount(fileDetail.getPages());

		int pageCount = fileDetail.getPages();
		int amount = 1;
		int sideAmount = 1;
		double sizeAmount = 1;

		if (requestDTO.getIsColor()) {
			amount = 5;
		}

		if (requestDTO.getSide() == PaperSideEnum.BOTH) {
			sideAmount = (pageCount / 2) + (pageCount / 4);
		} else {
			sideAmount = pageCount;
		}

		if (requestDTO.getSize() == PaperSizeEnum.A3) {
			sizeAmount = 1.2f;
		} else if (requestDTO.getSize() == PaperSizeEnum.A2) {
			sizeAmount = 1.5f;
		}

		// Calculate the price
		Double price = 0.0;

		price = amount * sideAmount * sizeAmount;
		
		price = price * requestDTO.getCopy();

		request.setPrice(price);

		request.setIsPaid(false);
		request.setStatus(TransactionStatusEnum.REQUESTED);

		return request;
	}

}
