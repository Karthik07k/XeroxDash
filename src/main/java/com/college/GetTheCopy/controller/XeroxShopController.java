package com.college.GetTheCopy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.college.GetTheCopy.common.dto.CommonResponse;
import com.college.GetTheCopy.dto.XeroxShop.XeroxShopResponseDTO;
import com.college.GetTheCopy.exception.NotFoundException;
import com.college.GetTheCopy.model.GeneralStatusEnum;
import com.college.GetTheCopy.model.XeroxShop;
import com.college.GetTheCopy.service.ShopService;

@RestController
@RequestMapping("api/v1")
public class XeroxShopController {

	private static final Logger logger = LoggerFactory.getLogger(XeroxShopController.class);

	@Autowired
	ShopService shopService;

	@GetMapping("/shops/{shopId}")
	public ResponseEntity<?> fetchShopByShopID(@PathVariable String shopId) throws IOException {
		logger.info("Entered CommonController-> fetchShopByShopID  Method");

		try {
			logger.info("fetchShopByShopId Method called");
			XeroxShop shop = shopService.fetchShopByShopId(shopId);

			XeroxShopResponseDTO shopResponseDTO = getShopResponseDTO(shop);
			logger.info("Exit CommonController-> fetchShopByShopID  Method");

			return new ResponseEntity<XeroxShopResponseDTO>(shopResponseDTO, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
			logger.info("Exit CommonController-> fetchShopByShopID  Method");
			throw e;
		}

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/shops")
	public ResponseEntity<?> getShop() throws IOException {
		logger.info("Entered CommonController-> fetchShops  Method");

		try {
			List<XeroxShop> shopList = new ArrayList<XeroxShop>();

			logger.info("fetchShopByUserName Method called");
			List<XeroxShop> shops = shopService.fetchShops();

			List<XeroxShopResponseDTO> shopResponseDTOs = getShopResponseDTOs(shops);

			logger.info("Exit CommonController-> fetchShops  Method");

			return new ResponseEntity<List<XeroxShopResponseDTO>>(shopResponseDTOs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
			logger.info("Exit CommonController-> fetchShops  Method");
			throw e;
		}

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/shops/{shopId}")
	public ResponseEntity<?> statusUpdate(@PathVariable String shopId, @RequestParam GeneralStatusEnum status) {
		logger.info("Entered Controller -> statusUpdate  Method");
		try {
			if (!shopService.isXeroxShopById(shopId)) {
				logger.error("Shop not found on the given ID");
				throw new NotFoundException("Shop not found on the given ID");
			}
			logger.info("updateShopStatus Method called");
			logger.debug("updateShopStatus Method called with shopId: " + shopId);
			shopService.updateShopStatus(shopId, status);

			logger.info("Exit ShopController -> statusUpdate  Method");

			CommonResponse response = new CommonResponse(true, "Successfully Updated");
			return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
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

	public List<XeroxShopResponseDTO> getShopResponseDTOs(List<XeroxShop> xeroxShops) {

		List<XeroxShopResponseDTO> shopResponseDTOs = new ArrayList<XeroxShopResponseDTO>();

		xeroxShops.forEach(xeroxShop -> shopResponseDTOs.add(getShopResponseDTO(xeroxShop)));

		return shopResponseDTOs;
	}

	public XeroxShopResponseDTO getShopResponseDTO(XeroxShop xeroxShop) {

		XeroxShopResponseDTO xeroxShopResponseDTO = new XeroxShopResponseDTO();

		xeroxShopResponseDTO.setId(xeroxShop.getId());
		xeroxShopResponseDTO.setName(xeroxShop.getName());
		xeroxShopResponseDTO.setEmailId(xeroxShop.getUserName());
		xeroxShopResponseDTO.setAddress(xeroxShop.getAddress());
		xeroxShopResponseDTO.setMobileNumber(xeroxShop.getMobileNumber());

		xeroxShopResponseDTO.setShopId(xeroxShop.getShopId());
		xeroxShopResponseDTO.setShopName(xeroxShop.getShopName());
		xeroxShopResponseDTO.setShopAddress(xeroxShop.getShopAddress());
		xeroxShopResponseDTO.setStartFrom(xeroxShop.getStartFrom());
		xeroxShopResponseDTO.setEndAt(xeroxShop.getEndAt());
		xeroxShopResponseDTO.setCostPerPage(xeroxShop.getCostPerPage());
		xeroxShopResponseDTO.setIsShopOpen(xeroxShop.getIsShopOpen());
		xeroxShopResponseDTO.setMetersAway(xeroxShop.getMetersAway());
		xeroxShopResponseDTO.setStatus(xeroxShop.getStatus());

		return xeroxShopResponseDTO;
	}

}
