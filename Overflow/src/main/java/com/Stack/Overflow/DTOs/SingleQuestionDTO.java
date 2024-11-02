package com.Stack.Overflow.DTOs;

import java.util.List;

import lombok.Data;

@Data
public class SingleQuestionDTO {

	private QuestionDTO questionDTO;
	
	private List<AnswerDto> answerDtosList;

	public QuestionDTO getQuestionDTO() {
		return questionDTO;
	}

	public void setQuestionDTO(QuestionDTO questionDTO) {
		this.questionDTO = questionDTO;
	}

	public List<AnswerDto> getAnswerDtosList() {
		return answerDtosList;
	}

	public void setAnswerDtosList(List<AnswerDto> answerDtosList) {
		this.answerDtosList = answerDtosList;
	}
	
	
	
}
