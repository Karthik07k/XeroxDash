package com.college.GetTheCopy.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.college.GetTheCopy.common.dto.FileStorageProperties;
import com.college.GetTheCopy.exception.FileStorageException;
import com.college.GetTheCopy.exception.MyFileNotFoundException;


@Service
public class FileStorageService {
	private final Path fileStorageLocation;
	
	private static final Logger logger= LoggerFactory.getLogger(FileStorageService.class);

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		logger.info("Entered Service -> FileStorageService Method");
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			logger.error(" Exception Occured "+ ex);
			logger.info("Exit Service -> FileStorageService Method");
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
		logger.info("Exit Service -> FileStorageService Method");
	}

	public String storeFile(MultipartFile file) {
		logger.info("Entered Service -> storeFile Method");
		// Normalize file name
		logger.info("cleanPath Method called");
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				logger.error("Sorry! Filename contains invalid path sequence " + fileName);
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			logger.info("resolve Method called");
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			System.out.println("Path ="+targetLocation);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Path ="+targetLocation);
			logger.info("Exit Service -> storeFile Method");
			return fileName;
		} catch (IOException ex) {
			logger.error(" Exception Occured "+ ex);
			logger.info("Exit Service -> storeFile Method");
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		logger.info("Entered Service -> loadFileAsResource Method");
		try {
			logger.info("resolve Method called");
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				logger.info("Exit Service -> loadFileAsResource Method");
				return resource;
			} else {
				logger.error("File not found " + fileName);
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			logger.error("MalformedURLException Occured " + ex);
			logger.info("Exit Service -> loadFileAsResource Method");
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}

		

}