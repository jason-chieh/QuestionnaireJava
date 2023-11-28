package com.example.questionnaire.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QuestionId implements Serializable {

	private int quid;
	
	private int qnid;

	public QuestionId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionId(int quid, int qnid) {
		super();
		this.quid = quid;
		this.qnid = qnid;
	}

	public int getQuid() {
		return quid;
	}

	public void setQuid(int quid) {
		this.quid = quid;
	}

	public int getQnid() {
		return qnid;
	}

	public void setQnid(int qnid) {
		this.qnid = qnid;
	}

	
	
}
