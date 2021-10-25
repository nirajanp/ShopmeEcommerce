package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // this is Spring configuration class. 
@EnableWebSecurity // And for security we need to use the annotation.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new ShopmeUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// this method configures authentication provider
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	// After Spring Security is added, at beginning of application login page is displayed by default.
	// this will override display of login page and will display custom defined login page named "/login"
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated()
				.and()
				.formLogin()
					.loginPage("/login")
					.usernameParameter("email") // we used username parameter as email that's why - by default username param is username.
					.permitAll()
					.and().logout().permitAll() // after permitAll() it is for remember me func.
					// just rememberMe() it self will remember password as long as application in not restarted
					// when application is restarted randomly generated key will be lost and remember-me cookie's 
					// information will be lost. If we add some default key then remember me functionality can be used
					// even when application is restarted.
					.and().rememberMe().key("AbcDefgHijklmnOpqrs_1234567890")
					// set token validity time
					.tokenValiditySeconds(7 * 24 * 60 * 60);  
	}
	
	// authenticated() will authenticate everything that comes to login page.
	// we are ignoring pictures inside images folder and /js/ folder
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	}
}
