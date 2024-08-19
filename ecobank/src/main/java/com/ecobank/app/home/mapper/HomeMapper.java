package com.ecobank.app.home.mapper;

import java.util.List;

import com.ecobank.app.home.service.ChallengeVO;

public interface HomeMapper {
	// 종료 챌린지 조회(1~5위)
	public List<ChallengeVO> getTopFiveFinishedChallenges();
	// 인기 챌린지(진행중, 1위, 참여자 수 기준)
	public ChallengeVO getMostPopularChallenge();
	
	// 종료 임박 챌린지(1~5)
	public List<ChallengeVO> getOverSoonChallenges();
}
