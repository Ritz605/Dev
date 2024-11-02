package com.Stack.Overflow.Model;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Ban {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	private Date banStartDate;
	
	private Date banEndDate;

	////////////////////////////do formatting for all tables
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getBanStartDate() {
		return banStartDate;
	}

	public void setBanStartDate(Date banStartDate) {
		this.banStartDate = banStartDate;
	}

	public Date getBanEndDate() {
		return banEndDate;
	}

	public void setBanEndDate(Date banEndDate) {
		this.banEndDate = banEndDate;
	}
	
	
	
	
}
