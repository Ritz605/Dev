package com.Stack.Overflow.DTOs;

import java.util.List;

import lombok.Data;

@Data
public class AllQuestionsResponseDTO {

	private List<QuestionDTO> questionDtoList;
	
	private Integer totalPages;
	
	private Integer pageNumber;

	public List<QuestionDTO> getQuestionDtoList() {
		return questionDtoList;
	}

	public void setQuestionDtoList(List<QuestionDTO> questionDtoList) {
		this.questionDtoList = questionDtoList;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
}
