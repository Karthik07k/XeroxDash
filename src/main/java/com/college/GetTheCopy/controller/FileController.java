package com.college.GetTheCopy.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.college.GetTheCopy.common.dto.CommonResponse;
import com.college.GetTheCopy.common.dto.ServicesBasepath;
import com.college.GetTheCopy.exception.ConflictException;
import com.college.GetTheCopy.model.FileDetail;
import com.college.GetTheCopy.model.FileFormatEnum;
import com.college.GetTheCopy.service.FileDetailService;
import com.college.GetTheCopy.service.FileStorageService;

@RestController
@RequestMapping("api/v1")
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private FileDetailService fileDetailService;

	@Autowired
	private ServicesBasepath servicesBasePath;

	@PostMapping("files/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

		logger.info("Entered CommonController-> uploadFile  Method");
		try {

			logger.info("storeFile Method called");
			logger.debug("storeFile Method called with file: " + file);
			String fileName = fileStorageService.storeFile(file);
			String fileDownloadUri = servicesBasePath.getPath() + "/api/v1/files/download/" + fileName;

			Integer pageCount = 0;
			FileFormatEnum fileFormat = FileFormatEnum.PDF;
			String filePath = "documents/" + fileName;
			File readFile = new File("documents/" + fileName);

			if (file.getContentType().contains("pdf")) {
				PDDocument document = PDDocument.load(readFile);
				pageCount = document.getNumberOfPages();
				document.close();
				fileFormat = FileFormatEnum.PDF;
			} else if (file.getContentType().contains("doc")) {
				Path msWordPath = Paths.get(filePath);
				XWPFDocument document = new XWPFDocument(Files.newInputStream(msWordPath));
				XWPFWordExtractor extractor = new XWPFWordExtractor(document);
				pageCount = extractor.getExtendedProperties().getPages();
				extractor.close();
				document.close();
				fileFormat = FileFormatEnum.DOCUMENT;
			} else if (file.getContentType().contains("jpeg")) {
				pageCount = 1;
				fileFormat = FileFormatEnum.JPEG;
			} else if (file.getContentType().contains("png")) {
				pageCount = 1;
				fileFormat = FileFormatEnum.PNG;
			}

			FileDetail fileDetail = new FileDetail(fileName, file.getSize(), fileDownloadUri, fileFormat, pageCount);

			logger.info("createFileDetail Method called");
			FileDetail fileDetailResponse = fileDetailService.createFileDetail(fileDetail);

			logger.info("Exit CommonController-> uploadFile  Method");
			return new ResponseEntity<FileDetail>(fileDetailResponse, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Exception occured: " + e);
			logger.info("Exit CommonController-> uploadFile  Method");
			throw e;
		}

	}

	@GetMapping("files/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
			throws IOException {
		// Load file as Resource
		logger.info("Entered CommonController-> downloadFile  Method");
		try {
			logger.info("loadFileAsResource Method called");
			logger.debug("loadFileAsResource Method called with fileName: " + fileName);
			Resource resource = fileStorageService.loadFileAsResource(fileName);

			String contentType = null;

			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			logger.info("Exit CommonController -> downloadFile  Method");
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);

		} catch (IOException ex) {
			logger.error("IOException Occured: " + ex);
			logger.info("Exit CommonController -> downloadFile  Method");
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception Occured: " + ex);
			logger.info("Exit CommonController -> downloadFile  Method");
			throw ex;
		}
	}

	@GetMapping("files")
	public Iterable<FileDetail> fetchFiles() {
		Iterable<FileDetail> fileDetail = fileDetailService.fetchFileDetails();
		return fileDetail;
	}

	@DeleteMapping("/files/{id}")
	public ResponseEntity<CommonResponse> fetchFiles(@PathVariable String id) throws Exception {
		try {
			fileDetailService.deleteFileDetail(id);
			CommonResponse commonResponse = new CommonResponse(true, "Deleted Successfully");

			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {

			throw new FileNotFoundException("File Not found");
		}

	}

}
