package com.Stack.Overflow.Model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.Stack.Overflow.Enums.VoteType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class QuestionVote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private VoteType voteType;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user-id", nullable = false)
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "question-id", nullable = false)
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Questions questions;

	private int voted;

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
	
	
	
}
