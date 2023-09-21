package com.college.GetTheCopy.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.college.GetTheCopy.model.FileDetail;
import com.college.GetTheCopy.repository.FileDetailRepository;

@Service
public class FileDetailService {
	

	@Autowired Environment env;

	public FileDetailService() {
		super();
	}

	@Autowired
	FileDetailRepository fileDetailRepository;
	
	private static final Logger logger= LoggerFactory.getLogger(FileDetailService.class);

	public FileDetail createFileDetail(FileDetail fileDetailDTO) {
		logger.info("Entered Service ->createFileDetail  Method");
		logger.info("save Method called");
		FileDetail fileDetail = fileDetailRepository.save(fileDetailDTO);
		logger.info("Exit Service ->createFileDetail  Method");
		return fileDetail;
	}
	
	
	public Iterable<FileDetail> fetchFileDetails() {
		logger.info("Entered Service -> fetchFileDetailDTO  Method");
		return fileDetailRepository.findAll();
	}
	

	public FileDetail fetchFileDetailByID(String fileDetailId) {
		logger.info("Entered Service -> fetchFileDetailDTO  Method");
		logger.debug("findById Method called with fileDetailId "+fileDetailId);
		Optional<FileDetail> fileDetail = fileDetailRepository.findById(fileDetailId);
		logger.info("Exit Service -> fetchFileDetailDTO  Method");
		return fileDetail.get();
	}
	
	public FileDetail fetchFileDetailByUrl(String fileUrl) {
		logger.info("Entered Service -> fetchFileDetailDTO  Method");
		logger.debug("findById Method called with fileDetailId "+fileUrl);
		FileDetail fileDetail = fileDetailRepository.findByUrl(fileUrl);
		logger.info("Exit Service -> fetchFileDetailDTO  Method");
		return fileDetail;
	}

	public void deleteFileDetail(String fileDetailId) {
		logger.info("Entered Service -> deleteFileDetail  Method");
		logger.debug("deleteById Method called with fileDetailId "+fileDetailId);
		fileDetailRepository.deleteById(fileDetailId);
		logger.info("Exit Service -> deleteFileDetail  Method");
	}
	

	public boolean isFileDetailExistById(String id) {
		return fileDetailRepository.existsById(id);
	}


}
