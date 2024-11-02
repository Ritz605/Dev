package com.Stack.Overflow.Model;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.Stack.Overflow.Enums.EditType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class QuestionEditRequest extends EditRequest{

	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "question_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Questions question;
	
	
	private String editedTitle;
	
	private List<String> editedTags;
	
	private String editedBody;
	
	private String editSummary;
	

	
	
	public Questions getQuestion() {
		return question;
	}

	public void setQuestion(Questions question) {
		this.question = question;
	}


	public String getEditedTitle() {
		return editedTitle;
	}

	public void setEditedTitle(String editedTitle) {
		this.editedTitle = editedTitle;
	}

	public List<String> getEditedTags() {
		return editedTags;
	}

	public void setEditedTags(List<String> editedTags) {
		this.editedTags = editedTags;
	}

	public String getEditedBody() {
		return editedBody;
	}

	public void setEditedBody(String editedBody) {
		this.editedBody = editedBody;
	}

	public String getEditSummary() {
		return editSummary;
	}

	public void setEditSummary(String editSummary) {
		this.editSummary = editSummary;
	}


	

	
	
}
