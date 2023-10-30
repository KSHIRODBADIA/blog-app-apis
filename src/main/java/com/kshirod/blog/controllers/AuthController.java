package com.kshirod.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kshirod.blog.payloads.JwtAuthRequest;
import com.kshirod.blog.payloads.JwtAuthResponse;
import com.kshirod.blog.payloads.UserDto;
import com.kshirod.blog.security.JwtTokenHelper;
import com.kshirod.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetail = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetail);
		JwtAuthResponse response = JwtAuthResponse.builder()
                .token(token)
                .username(userDetail.getUsername()).build();
//		JwtAuthResponse response = new JwtAuthResponse();
//		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
	}

	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken unp = new UsernamePasswordAuthenticationToken(username, password);
		this.authenticationManager.authenticate(unp);
	}
}
