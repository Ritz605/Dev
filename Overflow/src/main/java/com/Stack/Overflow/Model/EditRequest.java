package com.Stack.Overflow.Model;

import java.util.Date;

import com.Stack.Overflow.Enums.EditType;
import com.Stack.Overflow.Enums.ReviewStatus;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "edit_type")
public  abstract class EditRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User editor;
	
	@Enumerated(EnumType.STRING)
	private ReviewStatus reviewStatus;
	
	private int approvalCount = 0;
	private int rejectionCount = 0;
	
	private Date createdDate;
	
	public EditType getEditType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getEditor() {
		return editor;
	}

	public void setEditor(User editor) {
		this.editor = editor;
	}

	public ReviewStatus getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(ReviewStatus reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public int getApprovalCount() {
		return approvalCount;
	}

	public void setApprovalCount(int approvalCount) {
		this.approvalCount = approvalCount;
	}

	public int getRejectionCount() {
		return rejectionCount;
	}

	public void setRejectionCount(int rejectionCount) {
		this.rejectionCount = rejectionCount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public EditType getGetEditType() {
		return getEditType;
	}

	public void setGetEditType(EditType getEditType) {
		this.getEditType = getEditType;
	} 
	

	
}
