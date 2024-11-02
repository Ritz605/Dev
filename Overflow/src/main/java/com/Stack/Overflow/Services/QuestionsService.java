package com.Stack.Overflow.Services;

import com.Stack.Overflow.DTOs.AllQuestionsResponseDTO;
import com.Stack.Overflow.DTOs.QuestionDTO;
import com.Stack.Overflow.DTOs.QuestionSearchResponseDTO;
import com.Stack.Overflow.DTOs.SingleQuestionDTO;
import com.Stack.Overflow.Enums.ReviewStatus;
import com.Stack.Overflow.Model.QuestionEditRequest;
import com.Stack.Overflow.Model.Questions;
import com.Stack.Overflow.Model.User;

public interface QuestionsService {

	QuestionDTO addQuestion(QuestionDTO questionDTO);
	
	AllQuestionsResponseDTO getAllQuestions(int pageNumber);
	
	SingleQuestionDTO getQuestionById(Long questionId, Long userId);
	
	AllQuestionsResponseDTO getQuestionsByUserId(Long userId, int pageNumber);
	
	QuestionSearchResponseDTO searchQuestionByTitle(String title, int pageNum);
	
	QuestionSearchResponseDTO getLatestQuestion(int pageNum);
	
	//for editing
	Questions directEdit(Long questionId, Long userId, QuestionDTO questionDTO);
	
	QuestionEditRequest requestEdit(Long questionId, Long editorId, QuestionDTO questionDTO);
	
	void saveRevision(Questions question, User editor, String editSummary);
	
}
