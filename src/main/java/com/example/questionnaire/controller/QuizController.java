package com.example.questionnaire.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.JodaTimeConverters.DateTimeToDateConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.service.ifs.AddUserService;
import com.example.questionnaire.service.ifs.QuizService;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizSearchReq;
import com.example.questionnaire.vo.UserReq;
import com.example.questionnaire.vo.UserRes;
import com.example.questionnaire.vo.searchQuestionnaireListReq;

@RestController
@CrossOrigin      //這個是連前端
public class QuizController {
	
	@Autowired
	private QuizService service;
	
	@Autowired
	private AddUserService addUserService;

	@PostMapping(value = "api/quiz/create")
	public QuizRes create(@RequestBody QuizReq req) {
		return service.create(req);
	}
	
	@PostMapping(value = "api/quiz/update")
	public QuizRes update(@RequestBody QuizReq req) {
		return service.update(req);
	}
	
	
	@GetMapping(value = "api/quiz/search")
	public QuizRes search(@RequestBody QuizSearchReq req ) {
		String title = StringUtils.hasText(req.getTitle()) ? req.getTitle() : "";
		LocalDate startDate = req.getStartDate() != null ? req.getStartDate() : LocalDate.of(1971, 1, 1);
		LocalDate endDate = req.getEndDate() != null ? req.getEndDate() : LocalDate.of(2099, 1, 1);
		return service.search(title,startDate,endDate);
	}
	
	@GetMapping(value = "api/quiz/searchQuestionnaireList")
	public QuestionnaireRes searchQuestionnaireList(@RequestBody searchQuestionnaireListReq req) {
		return service.searchQuestionnaireList(req.getTitle(), req.getStartDate(), 
				req.getEndDate(), req.isPublished());
	}
	
	@PostMapping(value = "api/quiz/deleteQuestionnaire")
	public QuizRes deleteQuestionnaire(@RequestBody List<Integer> qnidList) {
		return service.deleteQuestionnaire(qnidList);
	}
	
	//----------------------------------------------以下測試中  測試都正常可以使用
	@GetMapping(value = "api/quiz/search1")
	public QuizRes search(@RequestParam(value = "title", required = false, defaultValue = "") String title,
				          @RequestParam(value = "startDate", required = false)@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate startDate,
				          @RequestParam(value = "endDate", required = false)@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate endDate) {
			title = StringUtils.hasText(title) ? title : "";
			startDate = startDate != null ? startDate : LocalDate.of(1971,01,01);
			endDate = endDate != null ? endDate : LocalDate.of(2099,01,01);
			return service.search(title, startDate, endDate);
	}
	
	
	
	@GetMapping(value = "api/quiz/searchQuestionnaireList1")
	public QuestionnaireRes searchQuestionnaireList1(
			@RequestParam(value = "title", required = false, defaultValue = "") String title,
		    @RequestParam(value = "startDate", required = false)@DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate startDate,
		    @RequestParam(value = "endDate", required = false)@DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate endDate,
		    @RequestParam(value = "isPublished", required = false) boolean isPublished) {
		title = StringUtils.hasText(title) ? title : "";
		startDate = startDate != null ? startDate : LocalDate.of(1971,01,01);
		endDate = endDate != null ? endDate : LocalDate.of(2099,01,01);
		return service.searchQuestionnaireList(title, startDate,endDate,isPublished);
	}
	
	@GetMapping(value = "api/quiz/searchQuestionList")
	public QuestionRes searchQuestionList(
			@RequestParam(value = "qnid", required = false) int qnid) {
		return service.searchQuestionList(qnid);
	}
	//----------------------------------------------以上測試中  測試都正常可以使用
	
	
	@PostMapping(value = "api/quiz/updateQuestionnaireList")
	public QuizRes updateQuestionnaireList(@RequestBody List<Integer> idList){
		return service.updateQuestionnaireList(idList);
	}
	
	
	//回答問題的人要新增近來
	@PostMapping(value = "api/quiz/addAnswer")
	public UserRes addAnswer(@RequestBody UserReq req) {
		return addUserService.create(req);
	}
	
//	//搜尋作答此問題的人
	@GetMapping(value = "api/quiz/searchUserPeople")
	public UserRes searchUserPeople(
			@RequestParam(value = "qnidid", required = false) int qnid) {
		  	return addUserService.search(qnid);

	}
	
}
