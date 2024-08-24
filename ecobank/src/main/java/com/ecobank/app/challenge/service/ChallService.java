package com.ecobank.app.challenge.service;

import java.util.List;
import java.util.Map;

import com.ecobank.app.common.service.Criteria;

public interface ChallService {
	//챌린지 전체 조회 - 관리자
	public List<ChallVO> challList(Criteria criteria);
	
	//챌린지 목록 개수(페이징)
	public int getTotal(Criteria criteria);
	
	//상태에 따른 챌린지 조회 - 회원
	public List<ChallVO> getDList(Criteria criteria);
	
	//챌린지 목록 개수(페이징) - 상태에 따른
	public int getTotalByState(Criteria criteria);
	
	//챌린지 개수 가져오기 - 총개수
	public int countChallengesByState(ChallVO challVO);
	
	//챌린지 단건 조회
	public ChallVO challInfo(ChallVO challVO);
	
	//챌린지 D2에 참여한 인원 카운트
	public int enterUserCount(int challNo);

	//챌린지 좋아요 전체 개수
	public int challLikeCnt(int challNo);
	
	//챌린지 좋아요 여부
	public int challLikeStatus(int userNo, int challNo);
	
	//챌린지 좋아요 등록
	public int challLikeInsert(ChallVO challVO);
	
	//챌린지 좋아요 삭제
	public int challLikeDelete(int userNo, int challNo);
	
	//챌린지 등록
	public int challInsert(ChallVO challVO);
	
	//챌린지 수정
	public Map<String, Object> challUpdate(ChallVO challVO);
	
	//챌린지 삭제
	public int challDelete(int challNo);
	
	//점수 목록
	public List<Map<String, Object>> scoreList(Criteria criteria);
	
	//점수 목록 개수 가져오기
	public int getScoreTotal(Criteria criteria);

	//챌린지 정렬
	public List<ChallVO> challengeSort(int userNo, int select);
	
	//챌린지 후기 목록
	public List<ReviewDTO> reviewList(ChallVO challVO);
	
	//챌린지 후기 평균 별점 구하기
	public Double getAvgStar(int challNo);
	
	//챌린지 후기 등록 여부
	public int reviewStatus(int userNo, int challNo);
	
	//챌린지 후기 등록
	public int reviewInsert(ReviewDTO reviewDTO);
	
	//챌린지 후기 삭제
	public int reviewDelete(int nowUserNo, int reviewNo);
	
}
