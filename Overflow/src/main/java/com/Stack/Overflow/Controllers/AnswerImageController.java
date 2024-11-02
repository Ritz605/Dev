package com.Stack.Overflow.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Stack.Overflow.Services.AnswerImageService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api")
public class AnswerImageController {

	@Autowired
	private AnswerImageService imageService;
	
	@PostMapping("/image/{answerId}")
	public ResponseEntity<String> uploadFile(@RequestParam MultipartFile multipartFile, @PathVariable Long answerId) throws java.io.IOException {
		try {
			imageService.storeFile(multipartFile, answerId);
			return ResponseEntity.ok("Image stored successfully");
		}catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
}
