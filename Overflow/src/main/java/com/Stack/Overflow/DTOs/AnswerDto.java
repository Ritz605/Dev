package com.Stack.Overflow.DTOs;

import java.util.List;

import com.Stack.Overflow.Model.AnswerImage;

public class AnswerDto {

	private Long id;
	
	private String body;
	
	private Long QuestionId;
	
	private Long userId;
	
	private String username;
	
	private AnswerImage file;
	
	private boolean approved;
	
	private int voted;
	
	
	private Integer voteCount;
	
	
	private List<CommentDTO> commentDTOList;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getQuestionId() {
		return QuestionId;
	}

	public void setQuestionId(Long questionId) {
		QuestionId = questionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AnswerImage getFile() {
		return file;
	}

	public void setFile(AnswerImage file) {
		this.file = file;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}


	public int getVoted() {
		return voted;
	}

	public void setVoted(int voted) {
		this.voted = voted;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

	public List<CommentDTO> getCommentDTOList() {
		return commentDTOList;
	}

	public void setCommentDTOList(List<CommentDTO> commentDTOList) {
		this.commentDTOList = commentDTOList;
	}
	
	
	
	
}
