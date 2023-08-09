package com.a304.ggong.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a304.ggong.entity.Machine;
import com.a304.ggong.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	// return값이 Vote이므로 service에서 entity -> dto로 넘겨주는 로직 필요!
	Optional<Vote> findByUser_UserNo(Long userNo);

	Optional<Vote> findByMachine_MachineNo(Long machineNo);

	Optional<Vote> findAllByQuestion_QuestionID(Long questionId);

	// Vote + Machine + User Fetch Join
	@Query("SELECT v.answer, v.voteDate, m.areaGu, m.name, u.ageRange FROM Vote v LEFT JOIN Machine m ON v.machine.machineNo = m.machineNo LEFT JOIN User u ON v.user.userNo = u.userNo WHERE v.question.group = :questionGroup")
	List<Vote> findAllWithMachineAndQuestionFetchJoin(@Param("questionGroup") int questionGroup);

	// group과 type에 따라 전체 answer count하기
	@Query("SELECT COUNT(v) FROM Vote v WHERE v.question.type = :questionType GROUP BY v.question.group HAVING v.question.group = :questionGroup")
	Long countByQuestionGroupAndQuestionType(@Param("questionGroup") int questionGroup, @Param("questionType") String questionType);

	// group별(지난주 or 이번주) type(공통 or 특화)에 따라 A or B(answer)판단해서 count 해주기
	@Query("SELECT COUNT(v) FROM Vote v WHERE v.question.type = :questionType AND v.answer = :answerType GROUP BY v.question.group HAVING v.question.group = :questionGroup")
	Long countByQuestionGroupAndAnswerTypeAndQuestionType(@Param("questionGroup") int questionGroup, @Param("answerType") int answerType,
		@Param("questionType") String questionType);

	//당일 수거함 사용자 수(실시간), 지난달 사용자 수 추출 메서드
	@Query("SELECT COUNT(v) FROM Vote v WHERE v.voteDate >= :startDate AND v.voteDate < :endDate")
	Long countByVoteDate(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

	//기기별 사용자 수(지난주) 추출 메서드
	@Query("SELECT COUNT(v.machine) FROM Vote v WHERE v.voteDate >= :startDate AND v.voteDate < :endDate AND v.machine = :machine")
	Long countByMachine(@Param("machine") String machine, @Param("startDate") Timestamp startDate,
		@Param("endDate") Timestamp endDate);

	// 지난달 데이터 삭제
	@Query("DELETE FROM Vote v WHERE v.voteDate = :deleteDate")
	void deleteByDate(@Param("deleteDate") Timestamp deleteDate);

	// 오늘, 어제 수거함 사용자 수
//	@Query("SELECT COUNT(v) FROM Vote v WHERE v.voteDate = :date")
//	Long countByDate(@Param("Date") Timestamp date);

}