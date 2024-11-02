package com.Stack.Overflow.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Stack.Overflow.Services.QuestionImageService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api")
public class QuestionImageController {

	private QuestionImageService questionImageService;
	
	@PostMapping("/image/{questionId}")
	public ResponseEntity<String> uploadFileForQuestion(@RequestParam MultipartFile multipartFile, @PathVariable Long questionId) throws java.io.IOException {
		try {
			questionImageService.storeFileForQuestion(multipartFile, questionId);
			return ResponseEntity.ok("Image stored successfully");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
