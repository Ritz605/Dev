package com.Stack.Overflow.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Stack.Overflow.DTOs.AnswerVoteDTO;
import com.Stack.Overflow.DTOs.QuestionVoteDTO;
import com.Stack.Overflow.Services.VoteService;


@RestController
@RequestMapping("/api")
public class VoteController {

	@Autowired
	private VoteService voteService;
	
	@PostMapping("/vote")
	public ResponseEntity<?> addVoteToQuestion(@RequestBody QuestionVoteDTO questionVoteDTO) {
		QuestionVoteDTO questionVotedDTO2 =  voteService.addVoteToQuestion(questionVoteDTO);
		if(questionVotedDTO2 == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(questionVotedDTO2);
	}
	
	@PostMapping("/answer/vote")
	public ResponseEntity<?> addVoteToAnswer(@RequestBody AnswerVoteDTO answerVoteDTO) {
		AnswerVoteDTO createdAnswerVoteDTO = voteService.addVoteToAnswer(answerVoteDTO);
		if(createdAnswerVoteDTO == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerVoteDTO);
	}
}
