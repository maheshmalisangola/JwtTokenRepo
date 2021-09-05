package com.javabrains.springsecurityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javabrains.springsecurityjwt.models.AuthenticationRequest;
import com.javabrains.springsecurityjwt.models.AuthenticationResponse;
import com.javabrains.springsecurityjwt.services.JwtUtils;

@RestController
public class HomeController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtils jwtUtils;

	
	@RequestMapping({"/hello"})
	public String hello() {
		return "Hello mahesh!!";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
	try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
				(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		
	} catch (BadCredentialsException e) {
		throw new Exception("Incorrect username or password",e);
	}
	
	final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
	
		String token = jwtUtils.GenerateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
}

