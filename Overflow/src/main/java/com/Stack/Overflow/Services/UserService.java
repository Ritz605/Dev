package com.Stack.Overflow.Services;

import com.Stack.Overflow.DTOs.SignUpRequest;
import com.Stack.Overflow.DTOs.UserDTO;

public interface UserService {

	UserDTO createUser(SignUpRequest signUpDTO);
	
	boolean hasUserWithEmail(String email);
}
