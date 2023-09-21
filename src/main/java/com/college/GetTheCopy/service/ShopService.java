package com.college.GetTheCopy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.college.GetTheCopy.exception.NotFoundException;
import com.college.GetTheCopy.model.GeneralStatusEnum;
import com.college.GetTheCopy.model.XeroxShop;
import com.college.GetTheCopy.repository.XeroxShopRepository;

@Service
public class ShopService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private XeroxShopRepository xeroxShopRepository;

	private static final Logger logger = LoggerFactory.getLogger(ShopService.class);

	public XeroxShop createShop(XeroxShop shop) {
		logger.info("Entered Service -> createShop  Method");
		shop.setPassword(passwordEncoder.encode(shop.getPassword()));
		logger.info("save Method called");
		logger.info("Exit Service -> createShop  Method");
		return xeroxShopRepository.save(shop);
	}

	public Long getShopCount() {
		return xeroxShopRepository.count();
	}

	public XeroxShop fetchShopByUserName(String username) {
		logger.info("Entered Service -> fetchShopByUserName Method");
		logger.info("fetchShopByUserName Method called");
		logger.info("Exit Service ->  fetchShopByUserName Method");
		return xeroxShopRepository.findByUserName(username);
	}

	public XeroxShop fetchShopByShopId(String shopId) {
		logger.info("Entered Service -> fetchShopByShopId Method");
		logger.info("fetchShopByShopId Method called");
		logger.info("Exit Service ->  fetchShopByShopId Method");

		return xeroxShopRepository.findByShopId(shopId);
	}

	public XeroxShop fetchShopById(String shopId) {
		logger.info("Entered Service -> fetchShopById Method");
		logger.info("fetchShopById Method called");
		logger.info("Exit Service ->  fetchShopById Method");
		return xeroxShopRepository.findById(shopId).get();
	}

	public List<XeroxShop> fetchShops() {
		logger.info("Entered Service -> fetchShops Method");
		logger.info("fetchShops Method called");
		logger.info("Exit Service ->  fetchShops Method");
		return xeroxShopRepository.findAll();

	}

	public boolean isXeroxShopById(String id) {
		return xeroxShopRepository.existsById(id);
	}

	public XeroxShop updateShopStatus(String shopId, GeneralStatusEnum status) {
		logger.info("Entered Service-> updateShopStatus  Method");

		logger.info("findById Called");
		logger.debug("findById Called with shopId: " + shopId);

		XeroxShop xerxoShop = xeroxShopRepository.findById(shopId).get();
		if (xerxoShop != null) {
			xerxoShop.setStatus(status);
			logger.info("Exit Service-> updateShopStatus  Method");
			return xeroxShopRepository.save(xerxoShop);
		} else {
			logger.error("Shop not found on the given ID");
			throw new NotFoundException("Shop not found on the given ID");
		}

	}

}
