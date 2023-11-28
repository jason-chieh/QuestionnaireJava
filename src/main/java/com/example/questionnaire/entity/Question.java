package com.example.questionnaire.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="question")
@IdClass(value=QuestionId.class)
public class Question {
	
	
	@Id
	@Column(name="id")
	private int quid;
	
	@Id
	@Column(name="qn_id")
	private int qnid;
	
	@Column(name ="q_title")
	private String qtitle;
	
	@Column(name ="option_type")
	private String optionType;
	
	@Column(name = "is_necessary")
	private boolean necessary;
	
	@Column(name="q_option")
	private String option;

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Question(int quid, int qnid, String qtitle, String optionType, boolean necessary, String option) {
		super();
		this.quid = quid;
		this.qnid = qnid;
		this.qtitle = qtitle;
		this.optionType = optionType;
		this.necessary = necessary;
		this.option = option;
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

	public String getQtitle() {
		return qtitle;
	}

	public void setQtitle(String qtitle) {
		this.qtitle = qtitle;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public boolean isNecessary() {
		return necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Question(int quid,String qtitle, String optionType, boolean necessary, String option) {
		super();
		this.quid = quid;
		this.qtitle = qtitle;
		this.optionType = optionType;
		this.necessary = necessary;
		this.option = option;
	}



	
	
	
}
