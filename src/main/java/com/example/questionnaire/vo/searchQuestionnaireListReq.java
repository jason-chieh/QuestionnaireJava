package com.example.questionnaire.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class searchQuestionnaireListReq {
	
	private String title = "";
	
	@JsonProperty("start_Date") //�i�H��postman�o�ӵ{�����ܼƹ�����ڭ�elispe���ܼ�
	private LocalDate startDate = null;
	
	@JsonProperty("end_Date") //�i�H��postman�o�ӵ{�����ܼƹ�����ڭ�elispe���ܼ�
	private LocalDate endDate = null;
	
	@JsonProperty("publish") //�i�H��postman�o�ӵ{�����ܼƹ�����ڭ�elispe���ܼ�
	private boolean isPublished =false;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isPublished() {
		return isPublished;
	}

	public void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}
	
	
}
