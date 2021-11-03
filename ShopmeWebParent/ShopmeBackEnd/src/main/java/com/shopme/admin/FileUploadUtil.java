package com.shopme.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	
	//this is utility class so we have methods as static here
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);
	public static void saveFile(String uploadDir, String fileName, 
			MultipartFile multipartFile) throws IOException {
		// path to upload photo.
		Path uploadPath = Paths.get(uploadDir);
		
		// create this directory if uploadPath does not exist
		if (!Files.exists(uploadPath)) {
			// create directory for the upload path
			Files.createDirectories(uploadPath);
		}
		
		try(InputStream inputStream = multipartFile.getInputStream()) {
			// path of the file relative to the upload directory
			Path filePath = uploadPath.resolve(fileName);
			// it will override the existing file with the same name
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			throw new IOException("Could not save file: " + fileName, ex);
		}
		
		
	}
	
	public static void cleanDir(String dir) {
		Path dirPath = Paths.get(dir);
		try {
			Files.list(dirPath).forEach(file -> {
				if (!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch(IOException ex) {
//						LOGGER.error("Could not delete file: " + file);
					}
				}
			});
		} catch (IOException ex) {
			LOGGER.error("Could not list directory " + dirPath);
//			System.out.println("Could not list directory " + dirPath);
		}
	}

}
