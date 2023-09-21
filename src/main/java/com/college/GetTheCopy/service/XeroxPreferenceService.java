package com.college.GetTheCopy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college.GetTheCopy.model.XeroxPreference;
import com.college.GetTheCopy.repository.XeroxPreferenceRepository;

@Service
public class XeroxPreferenceService {

	private static final Logger logger = LoggerFactory.getLogger(XeroxPreferenceService.class);
	
	@Autowired
	XeroxPreferenceRepository xeroxPreferenceRepository;
	
	public XeroxPreference createXeroxPreference(XeroxPreference xeroxPreference) {
		logger.info("Entered Service -> createXeroxPreference  Method");
		logger.info("save Method called");
		logger.info("Exit Service -> createOfficer  Method");
		return xeroxPreferenceRepository.save(xeroxPreference);
	}
}
