package com.ecobank.app.challenge.mapper;

import java.util.List;
import java.util.Map;

import com.ecobank.app.challenge.service.ChallVO;

public interface ChallMapper {
	//챌린지 전체 조회 - (state) 예정/진행/종료
	public List<ChallVO> selectChallAll(); //나중에 검색, 페이징 조건 매개값으로 넣어줘야함
	
	//챌린지 카운트 가져오기
	public int countAllChallenges(String challState);
	
	//챌린지 단건 조회 - challNo
	public ChallVO selectChallInfo(ChallVO challVO);
	
	//챌린지 번호 생성
	//public int selectChallNum();
	
	//챌린지 등록
	public int insertChallInfo(ChallVO challVO);
	
	//챌린지 수정
	public int updateChallInfo(ChallVO challVO);
	
	//챌린지 사진 update
	public int reUpdateChall(ChallVO challVO);
	
	//챌린지 삭제
	public int deleteChallInfo(int challNo);
	
	//인증 회원 점수 목록
	public List<Map<String, Object>> selectScoreAll();
	
}
