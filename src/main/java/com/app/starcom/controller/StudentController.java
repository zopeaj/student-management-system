package com.app.starcom.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import com.app.starcom.model.Student;
import com.app.starcom.repository.StudentRepository;
import com.app.starcom.util.FileUploadResponse;
import com.app.starcom.util.FileUploadUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/students/")
public class StudentController {
	
	private static Logger LOGGER = Logger.getLogger(StudentController.class);
	
	@Autowired
	private StudentRepository studentRepository;
	
	
	@PostMapping
	public ResponseEntity<FileUploadResponse>createStudent(@RequestParam("file") MultipartFile multipartFile, 
			@RequestParam("name") String name) throws IOException {
		FileUploadResponse response = new FileUploadResponse();
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			long size = multipartFile.getSize();
			String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
			
			response.setFileName(fileName);
			response.setSize(size);
			response.setDownloadUri("/downloadFile/img/data/" + filecode + "-" + multipartFile.getOriginalFilename());
			response.setContentType(multipartFile.getContentType());
			
			Student student = new Student(null, name, filecode);
			studentRepository.save(student);				
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/downloadFile/img/{fileCode}")
	public void downloadFile(@PathVariable("fileCode") String fileCode, HttpServletResponse response) throws IOException {
		Resource resource = null;
		try {
		    resource = FileUploadUtil.getFileAsResource(fileCode);
		}catch(IOException e) {
			throw new IOException(e.getMessage()); 
		}
	
		if(resource == null) {
			response.setStatus(404);
		}
	
	    LOGGER.info(String.format("Loading file fileCode: %s", fileCode));
	    response.addHeader("Content-Disposition", "attachment; filename=" + resource.getFilename());
	    IOUtils.copy(resource.getInputStream(), response.getOutputStream());
	    LOGGER.info(String.format("Sent file fileCode: %s", fileCode));
	}
	
	
	@GetMapping("/downloadFile/img/data/{fileCode}")
	public ResponseEntity<?> downloadFiles(@PathVariable("fileCode") String fileCode) throws IOException {
		Resource resource = null;
		try {
		    resource = FileUploadUtil.getFileAsResource(fileCode);
		}catch(IOException e) {
			return (ResponseEntity<?>) ResponseEntity.internalServerError();
			//throw new IOException(e.getMessage()); 
		}
	
		if(resource == null) {
			return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
		}
		String contentType = "image/png";
		String headerValue = "attachment; filename\"" + resource.getFilename() + "\"";
		//return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(headerValue).body(resource);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
	}
}
