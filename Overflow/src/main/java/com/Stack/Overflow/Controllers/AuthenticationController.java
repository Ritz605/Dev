package com.Stack.Overflow.Controllers;

import java.io.IOException;
import java.util.Optional;

import javax.swing.text.AbstractDocument.Content;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Stack.Overflow.DTOs.AuthenticationRequest;
import com.Stack.Overflow.DTOs.AuthenticationResponse;
import com.Stack.Overflow.Model.User;
import com.Stack.Overflow.Repositories.UserRepository;
import com.Stack.Overflow.utils.JwtUtils;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtils jwtUtils; 
	
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	
	@PostMapping("/authentication")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
		}catch (BadCredentialsException e) {
			System.out.println("bad credentials" + e.getMessage());
			throw new BadCredentialsException("Incorrect Email or passord");
		}catch (DisabledException disabledException) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created");
			return;
		}catch(Exception e) {
			System.out.println("Auth error" + e.getMessage());
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Auth failed");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		
		Optional<User> optionalUser =  userRepository.findFirstByEmail(userDetails.getUsername());
		
		final String jwt = jwtUtils.generateToken(userDetails.getUsername());
		
		if(optionalUser.isPresent()) {
			response.getWriter().write(new JSONObject()
					.put("userId", optionalUser.get().getId())
					.toString()
			);
		}
		
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, X-Requested-Width, Content-Type, Accept, X-Custom-Header");
		response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
		
	}
}
