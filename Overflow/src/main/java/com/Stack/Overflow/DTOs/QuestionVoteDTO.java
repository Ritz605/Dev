package com.Stack.Overflow.DTOs;

import com.Stack.Overflow.Enums.VoteType;

import lombok.Data;

@Data
public class QuestionVoteDTO {

	
	private Long id;
	
	private VoteType voteType;
	
	private Long userId;
	
	private Long questionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
	
	
}
