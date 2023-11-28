package com.example.questionnaire.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.vo.QnQuVo;

@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire,Integer> {

	/**	
	 *撈出全部資料後倒續找出最新資料 最新的資料會出現在第一筆 現在用不到~
	 **/
//	public Questionnaire findTopByOrderByIdDesc();
	
	public List<Questionnaire> findByIdIn (List<Integer> idList);
	
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(String title,LocalDate startDate ,LocalDate endDate);
	
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String title,LocalDate startDate ,LocalDate endDate);
	
	
	//使用musql的insert語法
	@Modifying
	@Transactional
	@Query(value ="insert into questionnaire(title,description,is_published,start_date,end_date)"
			+ " values (:title,:description,:isPublished,:startDate,:endDate)",nativeQuery = true)
	public int insert(
			@Param("title")String title,//
			@Param("description")String description,//
			@Param("isPublished")boolean isPublished,//
			@Param("startDate")LocalDate startDate,//
			@Param("endDate")LocalDate endDate);//
	
	//使用musql的insert語法
	@Modifying
	@Transactional
	@Query(value ="insert into questionnaire(title,description,is_published,start_date,end_date)"
			+ " values (?1,?2,?3,?4,?5)",nativeQuery = true)  //問號是下面insert的位置
	public int insertData(
			String title,//        ?1
			String description,//  ?2
			boolean isPublished,// ?3
			LocalDate startDate,// ?4
			LocalDate endDate);//  ?5
	
	//使用musql的update語法
	@Modifying
	@Transactional
	@Query(value = "update questionnaire set title = :title, description = :description where id = :id", nativeQuery=true)
	public int update(
			@Param("id") int id,//
			@Param("title") String title,//
			@Param("description") String description);//
	
	
	//使用musql的selete語法
	@Query(value="select * from questionnaire"
			+" where start_date > :startDate ",nativeQuery = true)
	public List<Questionnaire> findByStartDate(@Param("startDate")LocalDate startDate);
	
	//orderby & limit
//	1.limit query只能用在nativeQuery = true
//	limit要放到最後
//	SELECT * FROM questionnaire.questionnaire where start_date > '2023-11-01' order by id desc  limit 5;
//	語法orderby在資料庫要先寫 才可以限制筆數
	
	 
//	limit 後端分頁
	@Query(value = "select * from questionnaire "
			+ "limit :startIndex, :limitNum", nativeQuery = true)
	public List<Questionnaire> findWithLimitAndStartIndex(
			@Param("startIndex")int startIndex,//
			@Param("limitNum") int limitNum);
	
	
//	like模糊搜尋
	@Query(value = "select * from questionnaire "
			+ "where title like %:title%", nativeQuery = true)
	public List<Questionnaire> searchTitleLike(@Param("title") String title);
	
	
//	regexp模糊搜尋  只適用在 nativeQuery = true
	@Query(value = "select * from questionnaire "
			+ "where title regexp :title", nativeQuery = true)
	public List<Questionnaire> searchTitleregexp(@Param("title") String title);
	
//	regexp  加上|模糊搜尋  切記 資料庫只有一個|  
	@Query(value = "select * from questionnaire "
			+ "where title regexp :title |:title1", nativeQuery = true)
	public List<Questionnaire> searchTitleregexp2(@Param("title") String title,
													@Param("title1") String title1);
	
//	//	join兩個表連結
//	@Query("select new com.example.questionnaire.vo.QnQuVo("
//			+" qn.id, qn.title, qn.description, qn.published, qn.startDate, qn.endDate,"
//			+" q.quid, q.qtitle, q.optionType, q.necessary, q.option"
//			+" from Questionnaire as qn join Question as q on qn.id = q.qnid")
//	public List<QnQuVo> selectJoin();
	
	
	
//	@Query("select")
//	public List<Questionnaire> searchFuzzy(
//			@Param("title") String title,
//			@Param("startDate") String startDate,
//			@Param("endDate") String endDate);
}
