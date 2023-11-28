package com.example.questionnaire.constants;

public enum RtnCode {

	SUCCESSFUL(200,"OK"), //
	QUESTION_PARAM_ERROR(400,"Question Param Error"), //  
	QUESTIONNAIRE_PARAM_ERROR(400,"Questionnaire Param Error"), //  
	QUESTIONNAIRE_ID_PARAM_ERROR(400,"Questionnaire id Param Error"), // 
	QUESTIONNAIRE_ID_NOT_FOUND(404,"Questionnaire id not found"), // 
	UPDATE_ERROR(400,"update error"), // 
	DELETE_ERROR(400,"Delete error"), // 
	;
	
	private int code;
	
	private String message;

	private RtnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}


	
}

