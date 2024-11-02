package com.Stack.Overflow.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Stack.Overflow.DTOs.SignUpRequest;
import com.Stack.Overflow.DTOs.UserDTO;
import com.Stack.Overflow.Model.User;
import com.Stack.Overflow.Repositories.UserRepository;

@Service
public class UserServiceImpl  implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	public UserDTO createUser(SignUpRequest signUpDTO) {
		User user = new User();
		user.setEmail(signUpDTO.getEmail());
		user.setName(signUpDTO.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signUpDTO.getPassword()));
		User userCreated = userRepository.save(user);
		UserDTO createduserDTO = new UserDTO();
		createduserDTO.setId(userCreated.getId());
		return createduserDTO;
	}
	
	
	public boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}
}
