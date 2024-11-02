package com.Stack.Overflow.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Stack.Overflow.DTOs.AnswerVoteDTO;
import com.Stack.Overflow.DTOs.QuestionVoteDTO;
import com.Stack.Overflow.Enums.VoteType;
import com.Stack.Overflow.Model.AnswerVote;
import com.Stack.Overflow.Model.Answers;
import com.Stack.Overflow.Model.QuestionVote;
import com.Stack.Overflow.Model.Questions;
import com.Stack.Overflow.Model.User;
import com.Stack.Overflow.Repositories.AnswerRepository;
import com.Stack.Overflow.Repositories.AnswerVoteRepository;
import com.Stack.Overflow.Repositories.QuestionVoteRepository;
import com.Stack.Overflow.Repositories.QuestionsRepository;
import com.Stack.Overflow.Repositories.UserRepository;

@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private QuestionsRepository questionsRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private QuestionVoteRepository questionVoteRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private AnswerVoteRepository answerVoteRepository;
	@Override
	public QuestionVoteDTO addVoteToQuestion(QuestionVoteDTO questionVoteDTO) {
		Optional<Questions> optionalQuestion = questionsRepository.findById(questionVoteDTO.getQuestionId());
		Optional<User> optionalUser = userRepository.findById(questionVoteDTO.getUserId());
		if(optionalQuestion.isPresent() && optionalUser.isPresent()) {
			QuestionVote questionVote = new QuestionVote();
			Questions existingQuestion = optionalQuestion.get(); 
			questionVote.setVoteType(questionVoteDTO.getVoteType());
			if(questionVote.getVoteType() == VoteType.UPVOTE) {
				existingQuestion.setVoteCount(existingQuestion.getVoteCount() + 1);
			}else {
				existingQuestion.setVoteCount(existingQuestion.getVoteCount() - 1);
			}
			questionVote.setQuestions(optionalQuestion.get());
			questionVote.setUser(optionalUser.get());
			questionsRepository.save(existingQuestion);
			QuestionVote votedQuestion =  questionVoteRepository.save(questionVote);
			questionVoteDTO.setId(votedQuestion.getId());
			return questionVoteDTO;
		}
		
		return null;
	}

	@Override
	public AnswerVoteDTO addVoteToAnswer(AnswerVoteDTO answerVoteDTO) {
		Optional<Answers> optionalAnswer = answerRepository.findById(answerVoteDTO.getAnswerId());
		Optional<User> optionalUser = userRepository.findById(answerVoteDTO.getUserId());
		if(optionalAnswer.isPresent() && optionalUser.isPresent()) {
			Answers existingAnswer = optionalAnswer.get();
			User existingUser = optionalUser.get();
			AnswerVote answerVote = new AnswerVote();
			answerVote.setVoteType(answerVoteDTO.getVoteType());
			answerVote.setAnswer(existingAnswer);
			answerVote.setUser(existingUser);
			if(answerVote.getVoteType() == VoteType.UPVOTE) {
				existingAnswer.setVoteCount(existingAnswer.getVoteCount() + 1);
			}else {
				existingAnswer.setVoteCount(existingAnswer.getVoteCount() - 1);
			}
			answerRepository.save(existingAnswer);
			AnswerVote createdAnswerVote =  answerVoteRepository.save(answerVote);
			AnswerVoteDTO createAnswerVoteDTO = new AnswerVoteDTO();
			createAnswerVoteDTO.setId(createdAnswerVote.getId());
			return createAnswerVoteDTO;
		}
		return null;
		
	}
}
