package com.Stack.Overflow.DTOs;

import java.util.List;

import lombok.Data;

@Data
public class QuestionSearchResponseDTO {

	private List<QuestionDTO> questionDTOList;
	
	private Integer totalPages;
	
	private Integer pageNumber;

	public List<QuestionDTO> getQuestionDTOList() {
		return questionDTOList;
	}

	public void setQuestionDTOList(List<QuestionDTO> questionDTOList) {
		this.questionDTOList = questionDTOList;
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
