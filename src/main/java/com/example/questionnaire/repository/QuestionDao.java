package com.example.questionnaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.QuestionId;
import com.example.questionnaire.entity.Questionnaire;

@Repository
public interface QuestionDao extends JpaRepository<Question,QuestionId>{
	
	public void deleteAllByQnidIn(List<Integer> qnIdList);
	
	public void deleteAllByQnid(int Qnid);
	
	public void deleteAllByQnidAndQuidIn(int qnid,List<Integer> quIdList);
	
	public List<Question> findByQuidInAndQnid (List<Integer> idList , int qnid);
	
	public List<Question> findAllByQnidIn(List<Integer> qnids);
	
	
}
