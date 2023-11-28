package com.example.questionnaire.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.QuestionId;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.repository.QuestionDao;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.service.ifs.QuizService;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuestionnaireDao qnDao;

	@Autowired
	private QuestionDao quDao;

	// @transactional 加上這個springboot的插件會讓妳要跨表儲存，要馬全部成功要馬全部失敗
	@Transactional
	@Override
	public QuizRes create(QuizReq req) {
		QuizRes checkResult = checkParam(req);
		if (checkResult != null) {
			return checkResult;
		}
		int quid = qnDao.save(req.getQuestionnaire()).getId();
		List<Question> quList = req.getQuestionList();
		if (quList.isEmpty()) {
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		for (Question qu : quList) {
			qu.setQnid(quid);
		}
		quDao.saveAll(quList);
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	// 我獨立出來的方法!!!!!!
	private QuizRes checkParam(QuizReq req) {
		Questionnaire qn = req.getQuestionnaire();
		if (!StringUtils.hasText(qn.getTitle()) || !StringUtils.hasText(qn.getDescription())
				|| qn.getStartDate() == null || qn.getEndDate() == null || qn.getStartDate().isAfter(qn.getEndDate())) {
			return new QuizRes(RtnCode.QUESTIONNAIRE_PARAM_ERROR);
		}
		List<Question> quList = req.getQuestionList();
		for (Question qu : quList) {
			if (qu.getQuid() <= 0 || !StringUtils.hasText(qu.getQtitle()) || !StringUtils.hasText(qu.getOptionType())
					|| !StringUtils.hasText(qu.getOption())) {
				return new QuizRes(RtnCode.QUESTION_PARAM_ERROR);
			}
		}
		return null;
	}

	@Transactional
	@Override
	public QuizRes update(QuizReq req) {
		QuizRes checkResult = checkParam(req);
		if (checkResult != null) {
			return checkResult;
		}
		checkResult = checkQuestionnaireid(req);
		if (checkResult != null) {
			return checkResult;
		}
		Optional<Questionnaire> qnOp = qnDao.findById(req.getQuestionnaire().getId());
		if (qnOp.isEmpty()) {
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_NOT_FOUND);
		}
		List<Integer> deletedQuidList = new ArrayList<>();
		for(Question qu : req.getDeleteQuestionList()) {
			deletedQuidList.add(qu.getQuid());
		}
		Questionnaire qn = qnOp.get();

//		可以修改的條件:
//		尚未發布:is_published==false  ,可以修改
//		已發布但是尚未開始進行:is_published==true ,不可以修改 當時間必須小於start date
		if (!qn.isPublished() || (qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate()))) {
			quDao.deleteAllByQnid(qn.getId());
			qnDao.save(req.getQuestionnaire());
			quDao.saveAll(req.getQuestionList());		
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		return new QuizRes(RtnCode.UPDATE_ERROR);
	}

	
	private QuizRes checkQuestionnaireid(QuizReq req) {
		if (req.getQuestionnaire().getId() <= 0) {
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
		}
		List<Question> quList = req.getQuestionList();
		for (Question qu : quList) {
			if (qu.getQnid() != req.getQuestionnaire().getId()) {
				return new QuizRes(RtnCode.QUESTION_PARAM_ERROR);
			}
		}
		List<Question> quDelList = req.getDeleteQuestionList();
		
		for(Question qu:quDelList) {
			if(qu.getQnid()!=req.getQuestionnaire().getId()) {
				return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
			}
		}			
		
		return null;
	}

	@Transactional
	@Override
	public QuizRes deleteQuestionnaire(List<Integer> qnidList) {
		List<Questionnaire> qnList = qnDao.findByIdIn(qnidList);
		List<Integer> idList = new ArrayList<>();
		for (Questionnaire qn : qnList) {
			if (!qn.isPublished() || qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate())) {
				idList.add(qn.getId());
			}
		}
		if (!idList.isEmpty()) {
			qnDao.deleteAllById(idList);
			quDao.deleteAllByQnidIn(idList);
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		return new QuizRes(RtnCode.DELETE_ERROR);
	}

	@Transactional
	@Override
	public QuizRes deleteQuestion(int qnid, List<Integer> quidList) {
		Optional<Questionnaire> qnOp = qnDao.findById(qnid);
		if (qnOp.isEmpty()) {
			return new QuizRes(RtnCode.DELETE_ERROR);
		}
		Questionnaire qn = qnOp.get();
		if (!qn.isPublished() || qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate())) {
			quDao.deleteAllByQnidAndQuidIn(qnid,quidList);
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		return new QuizRes(RtnCode.DELETE_ERROR);
	}

	@Override
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate) {
		title = StringUtils.hasText(title) ? title : "";
		startDate = startDate != null ? startDate : LocalDate.of(1971, 1, 1);
		endDate = endDate != null ? endDate : LocalDate.of(2099, 1, 1);
		List<Questionnaire> qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(title, startDate, endDate);
		List<Integer> qnids = new ArrayList<>();
		for(Questionnaire qu:qnList){
			qnids.add(qu.getId());
		}
		List<Question> quList = quDao.findAllByQnidIn(qnids);
		List<QuizVo> quizVoList = new ArrayList<>();
		for(Questionnaire qn: qnList) {
			QuizVo vo = new QuizVo();
			vo.setQuestionnaire(qn);
			List<Question> questionList = new ArrayList<>();
			for(Question qu:quList) {
				if(qu.getQnid()==qn.getId()) {
					questionList.add(qu);
				}
			}
			vo.setQuestionList(questionList);
			quizVoList.add(vo);
		}
		return new QuizRes(quizVoList,RtnCode.SUCCESSFUL);
	}

	@Override
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, LocalDate endDate,boolean isPublished) {
		title = StringUtils.hasText(title) ? title : "";
		startDate = startDate != null ? startDate : LocalDate.of(1971, 1, 1);
		endDate = endDate != null ? endDate : LocalDate.of(2099, 1, 1);
		List<Questionnaire> qnList = new ArrayList<>();
		if(isPublished) {
			qnList =qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(title, startDate, endDate);
			return new QuestionnaireRes(qnList,RtnCode.SUCCESSFUL);
		}else {
			qnList =qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(title, startDate, endDate);
			return new QuestionnaireRes(qnList,RtnCode.SUCCESSFUL);
		}
	}

	@Override
	public QuestionRes searchQuestionList(int qnid) {
		if(qnid<=0) {
			return new QuestionRes(null,RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
		}
		List<Question> quList = quDao.findAllByQnidIn(Arrays.asList(qnid));
		return new QuestionRes(quList,RtnCode.SUCCESSFUL);
	}

	@Override
	public QuizRes searchFuzzy(String title, LocalDate startDate, LocalDate endDate) {
		
		return null;
	}
}
