package com.example.questionnaire.vo;

import java.util.ArrayList;
import java.util.List;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserVo {

	@JsonProperty("user_list")
	private List<User> userList= new ArrayList<>();

	public UserVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserVo(List<User> userList) {
		super();
		this.userList = userList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	
}
