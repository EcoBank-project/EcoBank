package com.ecobank.app.home.service;

import java.util.List;

public interface HomeService {
	// 종료 챌린지 조회(1~5위)
	public List<ChallengeVO> getTopFiveFinishedChallenges();
	
	// 인기 챌린지(진행중, 1위, 참여자 수 기준)
	public ChallengeVO getMostPopularChallenge();
	
	// 종료 임박 챌린지(1~5)
	public List<ChallengeVO> getOverSoonChallenges();
	
	// 회원 랭킹 조회(사용 점수 기준 1~3위)
	public List<RankingVO> getTopRankedUsers();
	
	// 챌린지 하나 무작위(예외처리용)
	public ChallengeVO getonechall();
}
