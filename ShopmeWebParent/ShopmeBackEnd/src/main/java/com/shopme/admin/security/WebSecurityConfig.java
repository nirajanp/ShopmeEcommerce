package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // this is Spring configuration class. 
@EnableWebSecurity // And for security we need to use the annotation.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// After Spring Security is added, at beginning of application login page is displayed by default.
	// this will override display of login page and will display database. 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().permitAll();
	}
	
}
