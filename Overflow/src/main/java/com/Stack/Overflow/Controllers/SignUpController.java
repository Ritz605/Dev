package com.Stack.Overflow.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Stack.Overflow.DTOs.SignUpRequest;
import com.Stack.Overflow.DTOs.UserDTO;
import com.Stack.Overflow.Services.UserService;


@RestController

public class SignUpController {
	
	@Autowired
	private UserService userService;


	@PostMapping({"/sign-up"})
	public ResponseEntity<?> createUser(@RequestBody(required = true) SignUpRequest signUpDTO) {
		
		if(userService.hasUserWithEmail(signUpDTO.getEmail())) {
			return new ResponseEntity<>("User already exist with this " + signUpDTO.getEmail(), HttpStatus.NOT_ACCEPTABLE);//406
			
		}
		UserDTO createdUserDTO =  userService.createUser(signUpDTO);
		
		if(createdUserDTO == null) {
			return new ResponseEntity<>("user not created, come again", HttpStatus.BAD_REQUEST);//400
		}else {
			 return new ResponseEntity<>(createdUserDTO, HttpStatus.OK);//200
		}
	}
}
