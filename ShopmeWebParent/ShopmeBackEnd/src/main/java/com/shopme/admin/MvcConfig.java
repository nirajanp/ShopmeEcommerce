package com.shopme.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// this class helps to display the user photos in the listing page 
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	// to make photo directory in the file system to be accessible to the
	// web client
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "user-photos";
		Path userPhotosDir = Paths.get(dirName);

		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
		// double aestrisk to allow all the files under this directory to be available
		// to the web clients
		// after that calling addResourceLocations to map addResourceHandler's parameter
		// to file path
		registry.addResourceHandler("/" + dirName + "/**")
			.addResourceLocations("file://" + userPhotosPath + "/");
		
		String categoryImgDirName = "../category-images";
		Path categoryPhotosDir = Paths.get(categoryImgDirName);

		String categoryPhotosPath = categoryPhotosDir.toFile().getAbsolutePath();
		// double aestrisk to allow all the files under this directory to be available
		// to the web clients
		// after that calling addResourceLocations to map addResourceHandler's parameter
		// to file path
		registry.addResourceHandler("/category-images/**")
			.addResourceLocations("file://" + categoryPhotosPath + "/");
	}
	
	

}
