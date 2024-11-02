package com.Stack.Overflow.Controllers;

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

import com.Stack.Overflow.DTOs.AnswerDto;
import com.Stack.Overflow.DTOs.CommentDTO;
import com.Stack.Overflow.DTOs.QuestionDTO;
import com.Stack.Overflow.Model.AnswerEditRequest;
import com.Stack.Overflow.Model.Answers;
import com.Stack.Overflow.Model.User;
import com.Stack.Overflow.Services.AnswerService;

import jakarta.annotation.Generated;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

	private final AnswerService answerService;
	
	public AnswerController(AnswerService answerService) {
		this.answerService = answerService;
	}
	
	@PostMapping
	public ResponseEntity<?> addAnswer(@RequestBody AnswerDto answerDto) {
		AnswerDto createdAnswerDto = answerService.postAnswer(answerDto);
		if(createdAnswerDto == null) {
			return new ResponseEntity<>("Something went wrong. ", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerDto);
	}
	
	
	@GetMapping("/answer/{answerId}")
	public ResponseEntity<AnswerDto> approveAnswer(@PathVariable Long answerId) {
		AnswerDto approvedAnswerDto = answerService.approveAnswer(answerId);
		if(approvedAnswerDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(approvedAnswerDto);
	}
	
	@PostMapping("/comment")
	public ResponseEntity<?> postCommentToAnswer(@RequestBody CommentDTO commentDTO) {
		CommentDTO postedCommentDTO = answerService.postCommentToAnswer(commentDTO);
		if(postedCommentDTO == null) {
			return new ResponseEntity<>("Something went wrong ", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(postedCommentDTO);
	}
	
	
	@PutMapping("/{answerId}/edit")
	public ResponseEntity<?> directEditAnswer(@PathVariable Long answerId, @RequestParam Long editorId, @RequestBody String body ,@RequestBody String editSummary) {
		Answers editedAnswer = answerService.directEdit(answerId, editorId, body, editSummary);
		if(editedAnswer == null) {
			return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not authorized for direct edit.");
		}
		return ResponseEntity.ok(editedAnswer);
	}
	
	@PostMapping("/requestEdit/{answerId}")
	public ResponseEntity<?> requestEdit(@PathVariable Long answerId, @RequestParam Long editorId, @RequestBody String editedBody, @RequestBody String editedSummary) {
		AnswerEditRequest editRequest = answerService.requestEdit(answerId, editorId, editedBody, editedSummary);
		if (editRequest == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user not authorized for edit");
		}
		return ResponseEntity.ok(editRequest);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
