package com.Stack.Overflow.Services;

import com.Stack.Overflow.DTOs.AnswerVoteDTO;
import com.Stack.Overflow.DTOs.QuestionVoteDTO;

public interface VoteService {

	
	QuestionVoteDTO addVoteToQuestion(QuestionVoteDTO questionVoteDTO);
	
	AnswerVoteDTO addVoteToAnswer(AnswerVoteDTO answerVoteDTO);
}
