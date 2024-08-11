package com.ecobank.app.challenge.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.challenge.service.ChallVO;
import com.ecobank.app.common.service.Criteria;

public interface ChallMapper {
	//챌린지 전체 조회 - 관리자용
	public List<ChallVO> selectChallAll(Criteria criteria); //나중에 검색(x), 페이징 조건(o) 매개값으로 넣어줘야함
	
	//챌린지 목록 개수(페이징)
	public int getTotal();
	
	//챌린지 조회 - 회원용(상태값에 따라)
	public List<ChallVO> getChallList(String challState);
	//public List<ChallVO> getChallList(@Param("criteria") Criteria criteria, @Param("challState") String challState);
	
	//챌린지 목록 개수(페이징) - 상태에 따른..
	//public int getTotalByState(String challState);
	
	//챌린지 개수 가져오기
	public int countAllChallenges(String challState);
	
	//챌린지 단건 조회 - 관리자용
	public ChallVO selectChallInfo(ChallVO challVO);
	
	//챌린지 번호 생성
	//public int selectChallNum();
	
	//챌린지 단건 조회 - 회원용
	//public ChallVO getChallInfo(ChallVO challVO);
	
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
