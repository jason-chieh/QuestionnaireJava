package com.example.questionnaire.vo;

import com.example.questionnaire.entity.User;

public class UserReq {
	
	private User user = new User();

	
	public UserReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserReq(User user) {
		super();
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
