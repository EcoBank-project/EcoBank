package com.ecobank.app.score.service;

import com.ecobank.app.intro.service.CarbUserVO;

public interface ScoreService {
	// 챌린지 참여 인원 조회
	public int getChallEnterUserCount();
	// 사이트 총 사용 점수 조회
	public int selectTotalUseScore();
	
	// 사이트 총 점수 조회
	public int selectTotalScore();
		
	// 회원 사용가능 점수 조회
	public String getScoreUsableByUserId(String useId);
	
	// 점수 사용
	public int insertToUseScore(ScoreVO scoreVO);
	
	String getUserNoFromId(CarbUserVO userVO);
	
	public String getUserId();
}
