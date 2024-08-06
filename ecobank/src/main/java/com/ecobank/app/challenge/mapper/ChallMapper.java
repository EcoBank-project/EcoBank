package com.ecobank.app.challenge.mapper;

import java.util.List;

import com.ecobank.app.challenge.service.ChallVO;

public interface ChallMapper {
	//챌린지 전체 조회 - (state) 예정/진행/종료
	public List<ChallVO> selectChallAll(); //나중에 검색, 페이징 조건 매개값으로 넣어줘야함
	
	//챌린지 단건 조회 - challNo
	public ChallVO selectChallInfo(ChallVO challVO);
	
	//챌린지 등록
	// : challNo, challTitle, challContent, challStartAt, challCloseAt..
	public int insertChallInfo(ChallVO challVO);
	
	//챌린지 수정
	public int updateChallInfo(ChallVO challVO);
	
}
