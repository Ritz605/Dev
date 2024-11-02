package com.Stack.Overflow.Model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.Stack.Overflow.DTOs.QuestionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Questions")
public class Questions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	@Lob
	@Column(name = "body", length = 512)
	private String body;
	
	private Date createdDate;
	
	private Integer voteCount = 0;
	
	@ElementCollection(targetClass = String.class)
	private List<String> tags;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	@OneToMany(mappedBy = "questions", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<QuestionVote> questionVotedList;
	
	public QuestionDTO getQuestionDTO() {
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setId(id);
		questionDTO.setBody(body);
		questionDTO.setTags(tags);
		questionDTO.setTitle(title);
		questionDTO.setVoteCount(voteCount);
		questionDTO.setCreatedDate(createdDate);
		questionDTO.setUserId(user.getId());
		questionDTO.setUsername(user.getName());
		return questionDTO;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

	public List<QuestionVote> getQuestionVotedList() {
		return questionVotedList;
	}

	public void setQuestionVotedList(List<QuestionVote> questionVotedList) {
		this.questionVotedList = questionVotedList;
	}
	
	
	
	
	
}
