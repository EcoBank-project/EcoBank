package com.ecobank.app.score.mapper;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.intro.service.CarbUserVO;
import com.ecobank.app.score.service.ScoreVO;

public interface ScoreMapper {
	// 사이트 총 사용 점수 조회
	public int selectTotalUseScore();
	
	// 사이트 총 점수 조회
	public int selectTotalScore();
	
	// 회원 아이디로부터 사용 가능한 점수를 가져오는 메서드
	public String selectScoreUsableByUserId(@Param("useId") String useId);
	
	// 종료 챌린지 건수 조회
	public int selectChallEnterUserCount();
	
	// 점수 사용
	public int insertToUseScore(ScoreVO scoreVO);
	
	// 로그인정보로부터 사용자번호 가져오는 쿼리
	public String getUserNoFromId(CarbUserVO userVO);
}
