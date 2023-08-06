package com.a304.ggong.service;

import java.util.List;

import com.a304.ggong.dto.response.AllAnswerResponse;
import com.a304.ggong.dto.response.AnswerDetailResponse;

public interface AnswerService {

	// service단에서 각각의 resDto에 answerA, answerB와 rateA, rateB의 값을 넣어줘야함.

	// 질문 상세 페이지(대학)
	public List<AnswerDetailResponse>[] getDetailAnswer(int questionGroup, String machineLocation);

	// 공통 질문 응답 데이터 조회
	public List<AllAnswerResponse> getAnswersGroupByCommon(int questionGroup);

	// 대학 질문 응답 데이터 조회
	public List<AllAnswerResponse> getAnswersGroupByUnis(int questionGroup);

	// 기업 질문 응답 데이터 조회
	public List<AllAnswerResponse> getAnswersGroupByCompanies(int questionGroup);
}