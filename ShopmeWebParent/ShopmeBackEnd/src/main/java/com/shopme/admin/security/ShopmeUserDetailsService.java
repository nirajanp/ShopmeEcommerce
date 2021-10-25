package com.shopme.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopme.admin.user.UserRepository;
import com.shopme.common.entity.User;

// this class will be called by the spring security when it is performing the authentication process.
public class ShopmeUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		
		System.out.println("User: " + user);
		if (user != null) {
			return new ShopmeUserDetails(user);
		}
		
		// this exception class is defined by spring security
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}

}
