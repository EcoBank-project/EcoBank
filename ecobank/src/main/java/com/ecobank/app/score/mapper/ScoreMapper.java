package com.ecobank.app.score.mapper;

import org.apache.ibatis.annotations.Param;

public interface ScoreMapper {
	// 사이트 총 사용 점수 조회
	public int selectTotalUseScore();
	
	// 사이트 총 점수 조회
	public int selectTotalScore();
	
	// 회원 아이디로부터 사용 가능한 점수를 가져오는 메서드
	public int selectScoreUsableByUserId(@Param("useId") String useId);
}
