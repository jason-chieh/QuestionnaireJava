package com.example.questionnaire.vo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.fasterxml.jackson.annotation.JsonProperty;


public class QuizReq extends QuizVo {
	
	private List<Question> deleteQuestionList = new ArrayList<>();
	
	public QuizReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizReq(Questionnaire questionnaire, List<Question> questionList) {
		super(questionnaire, questionList);
		// TODO Auto-generated constructor stub
	}

	public List<Question> getDeleteQuestionList() {
		return deleteQuestionList;
	}

	public void setDeleteQuestionList(List<Question> deleteQuestionList) {
		this.deleteQuestionList = deleteQuestionList;
	}
	
	

	
}
