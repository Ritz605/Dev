package com.Stack.Overflow.DTOs;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CommentDTO {

	
	private Long id;
	
	private String body;
	
	private Date createdDate;
	
	private Long user_id;
	
	private Long answer_id;

	private String username;
	
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getAnswer_id() {
		return answer_id;
	}

	public void setAnswer_id(Long answer_id) {
		this.answer_id = answer_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<CommentDTO> getCommentDTOList() {
		return commentDTOList;
	}

	public void setCommentDTOList(List<CommentDTO> commentDTOList) {
		this.commentDTOList = commentDTOList;
	}
	
	
	
	
}
