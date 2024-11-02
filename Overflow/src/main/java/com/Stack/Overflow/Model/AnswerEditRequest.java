package com.Stack.Overflow.Model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AnswerEditRequest extends EditRequest {

	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "answer_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Answers answer;
	

	private String editedBody;
	
	private String editSummary;

	public Answers getAnswer() {
		return answer;
	}

	public void setAnswer(Answers answer) {
		this.answer = answer;
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
