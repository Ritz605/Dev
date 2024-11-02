package com.Stack.Overflow.Model;

import java.util.Date;
import java.util.List;

import com.Stack.Overflow.DTOs.AnswerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Answers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(name = "body", length = 512)
	private String body;
	
	private Date createdDate;
	
	private boolean approved = false;

	private Integer voteCount = 0;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "question_id", nullable = false)
	@JsonIgnore
	private Questions questions;

	@OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<AnswerVote> answerVoteList;
	
	@OneToMany(mappedBy = "answers", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Comments> commentList;
	
	public AnswerDto getAnswerDto() {
		AnswerDto answerDto = new AnswerDto();
		answerDto.setId(id);
		answerDto.setBody(body);
		answerDto.setApproved(approved);
		answerDto.setVoteCount(voteCount);
		answerDto.setUsername(user.getName());
		answerDto.setQuestionId(questions.getId());
		answerDto.setUserId(user.getId());
		return answerDto;
	}
	
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Questions getQuestions() {
		return questions;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

	public List<AnswerVote> getAnswerVoteList() {
		return answerVoteList;
	}

	public void setAnswerVoteList(List<AnswerVote> answerVoteList) {
		this.answerVoteList = answerVoteList;
	}

	public List<Comments> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comments> commentList) {
		this.commentList = commentList;
	}
	
	
	
}
