package com.ecobank.app.score.service;

public interface ScoreService {
	// 챌린지 참여 인원 조회
	public int getChallEnterUserCount();
	// 사이트 총 사용 점수 조회
	public int selectTotalUseScore();
	
	// 사이트 총 점수 조회
	public int selectTotalScore();
		
	// 회원 사용가능 점수 조회
	public int getScoreUsableByUserId(String useId);
}
