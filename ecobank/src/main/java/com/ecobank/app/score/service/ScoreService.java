package com.ecobank.app.score.service;

public interface ScoreService {
	// 종료 챌린지 건수 조회
	
	// 사이트 총 사용 점수 조회
	public int selectTotalUseScore();
	
	// 사이트 총 점수 조회
	public int selectTotalScore();
		
	// 회원 사용가능 점수 조회
	public int getScoreUsableByUserId(String useId);
}
