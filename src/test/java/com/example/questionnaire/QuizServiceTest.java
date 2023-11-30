package com.example.questionnaire;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.repository.UserDao;
import com.example.questionnaire.service.ifs.QuizService;
import com.example.questionnaire.vo.QnQuVo;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;

@SpringBootTest
public class QuizServiceTest {
	
	@Autowired
	private QuizService service;
	
	@Autowired
	private QuestionnaireDao qnDao;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void createTest() {
		Questionnaire questionnaire = new Questionnaire
				("test3","test",false,LocalDate.of(2023, 12, 30),LocalDate.of(2023, 12, 31));
		List<Question> questionList = new ArrayList<>();
		Question q1 = new Question(1,"test_question1","single",false,"aaa;bbb;ccc");
		Question q2 = new Question(2,"test_question2","multi",false,"10;20;30;40");
		Question q3 = new Question(3,"test_question3","text",false,"abc");
		questionList.addAll(Arrays.asList(q1, q2, q3));
		
		QuizReq req = new QuizReq(questionnaire,questionList);
		QuizRes res = service.create(req);
		Assert.isTrue(res.getRtnCode().getCode()==200,"create error");
	}
	
	@Test
	public void updateTest() {
		Questionnaire questionnaire = new Questionnaire
		(12,"test1update12","testupdate",false,LocalDate.of(2023, 11, 30),
				LocalDate.of(2023, 11, 30));
		List<Question> questionList = new ArrayList<>();
		Question q1 = new Question(1,12,"test_question13333333","single",false,"aaa;bbb;ccc");
		Question q2 = new Question(2,12,"test_question23333333","multi",false,"10;20;30;40");
		Question q3 = new Question(3,12,"test_question33333333_update","text",false,"abc");
		questionList.addAll(Arrays.asList(q1, q2,q3));
		
		QuizReq req = new QuizReq(questionnaire,questionList);
		QuizRes res = service.update(req);
		System.out.println(res.getRtnCode().getCode());
		System.out.println(res.getRtnCode().getMessage());
		Assert.isTrue(res.getRtnCode().getCode()==200,"create error");
	}
	
	@Test
	public void deleteQuestionnaireTest() {
		List<Integer> qnidList = Arrays.asList(23,24);
		QuizRes res = service.deleteQuestionnaire(qnidList);
		System.out.println(res.getRtnCode().getCode());
		System.out.println(res.getRtnCode().getMessage());
		Assert.isTrue(res.getRtnCode().getCode()==200,"create error");
	}
	
	@Test
	public void deleteQuestionTest() {
		List<Integer> quidList = new ArrayList<>(Arrays.asList(1,2,3));
		QuizRes res = service.deleteQuestion(20,quidList);
		System.out.println(res.getRtnCode().getCode());
		System.out.println(res.getRtnCode().getMessage());
	}
	
	@Test
	public void searchTest() {
		QuizRes res = service.search(null, null, LocalDate.of(2023, 12,15 ));
		System.out.println(res.getRtnCode().getCode());
		System.out.println(res.getRtnCode().getMessage());
		List<QuizVo> quizVoList = res.getQuizVoList();
		for(QuizVo item:quizVoList) {
			List<Question> question = item.getQuestionList();
			for(Question itt:question) {
				System.out.println(itt.getQtitle());
			}
			System.out.println(item.getQuestionnaire().getTitle());
		}
		Assert.isTrue(res.getRtnCode().getCode()==200,"create error");
	}
	
	@Test
	public void frontSearchTest() {
		QuestionnaireRes res = service.searchQuestionnaireList(null, null,null,false);
		List<Questionnaire> QuestionnaireList = res.getQuestionnaireList();
		for(Questionnaire item:QuestionnaireList) {
			System.out.println(item.getTitle());
		}
		Assert.isTrue(res.getRtnCode().getCode()==200,"create error");
	}
	
	@Test
	public void searchQuestionList() {
		QuestionRes res = service.searchQuestionList(21);
		List<Question> QuestionList = res.getQuestionList();
		for(Question item:QuestionList) {
			System.out.println(item.getQtitle());
		}
		Assert.isTrue(res.getRtnCode().getCode()==200,"create error");
	}
	
	@Test
	public void insertTest() {
		int res =qnDao.insert("qa_01", "qa_01_test", false,LocalDate.of(2023,11,24), LocalDate.of(2023,12,24));
		System.out.println(res);  //用變數是要看有沒有成功
	
	}
	@Test
	public void insertTest1() {
		int res =qnDao.insertData("qa_03", "qa_1_test", false,LocalDate.of(2023,11,24), LocalDate.of(2023,12,24));
		System.out.println(res);  //用變數是要看有沒有成功
	}
	
	@Test
	public void update() {
		int res =qnDao.update(76,"美麗", "美麗");
		System.out.println(res);  //用變數是要看有沒有成功
	}
	
	@Test
	public void selectTest() {
		List<Questionnaire> res =qnDao.findByStartDate(LocalDate.of(2023,11,24));
		System.out.println(res.size());  //用變數是要看有沒有成功
	}
	
	
//	這個要測試後端分頁 //	limit 後端分頁
	@Test
	public void limitTest() {
		List<Questionnaire> res =qnDao.findWithLimitAndStartIndex(1, 3);
		for( Questionnaire item:res) {
			System.out.println(item.getId());
		}
	}
	
//	like模糊搜尋測試
	@Test
	public void likeTest() {
		List<Questionnaire> res =qnDao.searchTitleLike("test");
		for( Questionnaire item:res) {
			System.out.println(item.getId());
		}
	}
//	
////	join測試
//	@Test
//	public void joinTest() {
//		List<QnQuVo> res =qnDao.selectJoin();
//		for( QnQuVo item:res) {
//			System.out.printf("id: %d, title: %s, qu_id: %d \n",
//					item.getId(), item.getTitle(),item.getQuid());
//		}
//	}
	
	@Test
	public void userTest() {
		List<User> user =userDao.findByQnidOrderByDateTime(215);
		for( User item:user) {
			System.out.println(item.getName());
		}
	}
	
	
}
