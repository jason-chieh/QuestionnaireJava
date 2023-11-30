package com.example.questionnaire.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.repository.UserDao;
import com.example.questionnaire.service.ifs.AddUserService;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.UserReq;
import com.example.questionnaire.vo.UserRes;

@Service
public class AddUserServiceImpl implements AddUserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserRes create(UserReq req) {
		User user = req.getUser();
		userDao.save(user);
		return new UserRes(null,RtnCode.ANSWER_SUCESS);
	}

	@Override
	public UserRes search(int qnid) {
		List<User> userL = userDao.findByQnidOrderByDateTime(qnid);
		return new UserRes(userL,RtnCode.ANSWER_SUCESS);
	}

}
