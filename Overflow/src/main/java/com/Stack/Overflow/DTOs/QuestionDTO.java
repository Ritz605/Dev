package com.Stack.Overflow.DTOs;

import java.util.Date;
import java.util.List;

import com.Stack.Overflow.Model.QuestionImage;

import lombok.Data;

@Data
public class QuestionDTO {

	private Long id;
	
	private String title;
	
	private String body;
	
	private List<String> tags;
	
	private Long userId;
	
	private String username;

	private Date createdDate;
	
	private Integer voteCount;
	
	
	private int voted;
	
	private QuestionImage file;
	
	private boolean hasApprovedAnswer = false;
	
	private String editSummary;
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

	public int getVoted() {
		return voted;
	}

	public void setVoted(int voted) {
		this.voted = voted;
	}

	public boolean isHasApprovedAnswer() {
		return hasApprovedAnswer;
	}

	public void setHasApprovedAnswer(boolean hasApprovedAnswer) {
		this.hasApprovedAnswer = hasApprovedAnswer;
	}

	public QuestionImage getFile() {
		return file;
	}

	public void setFile(QuestionImage file) {
		this.file = file;
	}

	public String getEditSummary() {
		return editSummary;
	}

	public void setEditSummary(String editSummary) {
		this.editSummary = editSummary;
	}

	
	
	
	
}
