package com.shopme.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.user.UserService;

//this is Spring RESTful web-service controller, so we need to use this annotation.
@RestController 
public class UserRestController {
	
	@Autowired
	private UserService service;
	
	// method to check the uniqueness email of user
	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(@Param("id") Integer id,@Param("email") String email) {
		return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
		
	}
}
