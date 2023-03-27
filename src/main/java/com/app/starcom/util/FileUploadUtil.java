package com.app.starcom.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	
	private static Path foundFile;
	
	public static String saveFile(String fileName, MultipartFile multipartFile) throws IOException{
		
	    Path uploadPath = Paths.get("Files-upload");

	    if(!Files.exists(uploadPath)){
	         Files.createDirectories(uploadPath);
	    }

	    String fileCode = RandomStringUtil.randomAlphanumeric(25, RandomStringUtil.RANDOM_ALPHANUMERICSTRING_DATA);

	    try(InputStream inputStream = multipartFile.getInputStream()) {
	    	Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
	        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	    }catch(IOException ioe) {
	    	throw new IOException("Could not save file: " + fileName, ioe);
	    }
	    return fileCode;
	}
	  
	public static Resource getFileAsResource(String fileCode) throws IOException {
		Path dirPath = Paths.get("Files-Upload");
	
		Files.list(dirPath).forEach(file -> {
			if(file.getFileName().toString().startsWith(fileCode)){
				foundFile = file;
				return;
			}
		});
	
		if(foundFile != null) {
			return new UrlResource(foundFile.toUri());
		}
		return null;
	}
	
	public static void getFileAsResource() throws IOException {
		Path dirPath = Paths.get("Files-Upload");
		Files.list(dirPath).forEach(file -> {
			System.out.println(file.getFileName().toString());
		});
	}	
	public static void main(String[] args) throws IOException {
		//System.out.println(FileUploadUtil.getFileAsResource(fileCode).getFilename());
		FileUploadUtil.getFileAsResource();
	}
}