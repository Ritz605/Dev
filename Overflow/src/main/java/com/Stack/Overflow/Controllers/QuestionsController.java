package com.Stack.Overflow.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.tags.EditorAwareTag;

import com.Stack.Overflow.DTOs.AllQuestionsResponseDTO;
import com.Stack.Overflow.DTOs.QuestionDTO;
import com.Stack.Overflow.DTOs.QuestionSearchResponseDTO;
import com.Stack.Overflow.DTOs.SingleQuestionDTO;
import com.Stack.Overflow.Enums.EditType;
import com.Stack.Overflow.Enums.ReviewStatus;
import com.Stack.Overflow.Model.EditRequest;
import com.Stack.Overflow.Model.QuestionEditRequest;
import com.Stack.Overflow.Model.Questions;
import com.Stack.Overflow.Model.User;
import com.Stack.Overflow.Repositories.UserRepository;
import com.Stack.Overflow.Services.EditService;
import com.Stack.Overflow.Services.QuestionsService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api")
public class QuestionsController {

	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private EditService editService;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/question")
	public ResponseEntity<?> postQuestion(@RequestBody QuestionDTO questionDTO) {
		QuestionDTO createdQuestionDTO = questionsService.addQuestion(questionDTO);
		if(createdQuestionDTO == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDTO);
	}
	
	@GetMapping("/question/{pageNumber}")
	public ResponseEntity<AllQuestionsResponseDTO> getAllQuestions(@PathVariable int pageNumber) {
		AllQuestionsResponseDTO allQuestionsResponseDTO = questionsService.getAllQuestions(pageNumber);
		return ResponseEntity.ok(allQuestionsResponseDTO);
	}
	
	@GetMapping("/question/{questionId}/{userId}")
	public ResponseEntity<?> getQuestionById(@PathVariable Long questionId, @PathVariable Long userId) {
		SingleQuestionDTO singleQuestionDTO = questionsService.getQuestionById(questionId, userId);
		if(singleQuestionDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(singleQuestionDTO);
	}
	
	
	@GetMapping("/question/{userId}/{pageNumber}")
	public ResponseEntity<AllQuestionsResponseDTO> getQuestionsByUserId(@PathVariable Long userId , @PathVariable int pageNumber) {
		AllQuestionsResponseDTO allQuestionsResponseDTO = questionsService.getQuestionsByUserId(userId, pageNumber);
		return ResponseEntity.ok(allQuestionsResponseDTO);
	}
	
	@GetMapping("/search/title/{pageNumber}")
	public ResponseEntity<?> searchQuestionByTitle(@PathVariable int pageNum,@PathVariable String title) {
		QuestionSearchResponseDTO questionSearchResponseDTO =  questionsService.searchQuestionByTitle(title, pageNum);
		if(questionSearchResponseDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(questionSearchResponseDTO);
	}
	
	@GetMapping("/question/latest/{pageNumber}")
	public ResponseEntity<?> getLatestQuestion(@PathVariable int pageNumber) {
		QuestionSearchResponseDTO latestQuestionSearchResponseDTO = questionsService.getLatestQuestion(pageNumber);
		if(latestQuestionSearchResponseDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(latestQuestionSearchResponseDTO);
		
	}
	
	@PutMapping("/{questionId}/edit")
	public ResponseEntity<?> directEditQuestion(@PathVariable Long questionId, @RequestParam Long userId, @RequestBody QuestionDTO questionDTO) {
		Questions updatedQuestion = questionsService.directEdit(questionId, userId, questionDTO);
		if(updatedQuestion != null) {
			return ResponseEntity.ok(updatedQuestion);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Edit not permitted");
	}
	
	@PostMapping("/{questionId}/requestEdit")
	public ResponseEntity<?> requestEdit(@PathVariable Long questionId,@RequestParam Long userId, @RequestBody QuestionDTO questionDTO) {
		QuestionEditRequest editRequest = questionsService.requestEdit(questionId, userId, questionDTO);
		return ResponseEntity.ok(editRequest);
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
