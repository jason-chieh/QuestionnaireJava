package com.example.questionnaire.service.ifs;

import java.util.List;

import com.example.questionnaire.vo.UserReq;
import com.example.questionnaire.vo.UserRes;

public interface AddUserService {
	
	public UserRes create (UserReq req); 
	
	public UserRes search (int qnid);
}
