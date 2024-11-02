package com.Stack.Overflow.DTOs;

import com.Stack.Overflow.Enums.VoteType;
import com.Stack.Overflow.Model.Answers;
import com.Stack.Overflow.Model.User;

import lombok.Data;

@Data
public class AnswerVoteDTO {

	
	private Long id;
	
	private VoteType voteType;
	
	private Long userId;
	
	private Long answerId;

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

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	
	
	
	
}
