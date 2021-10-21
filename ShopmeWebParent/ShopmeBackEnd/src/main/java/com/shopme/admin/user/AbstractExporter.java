package com.shopme.admin.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

public class AbstractExporter{
	
	public void setResponseHeader(HttpServletResponse response, String contentType, String extension) {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = dateFormatter.format(new Date());
		
		String fileName = "users_" + timestamp + extension ;
		
		// say some information to HttpServletResponse response so that
		// the browser can download the data as csv file
		
		response.setContentType(contentType);
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);
	}

}
