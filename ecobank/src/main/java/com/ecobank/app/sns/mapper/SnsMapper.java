package com.ecobank.app.sns.mapper;

import java.util.List;

import com.ecobank.app.sns.service.SnsVO;

public interface SnsMapper {
	
	//전체조회
	public List<SnsVO> selectSnsAll();
	
	//단건조회 
	public SnsVO selectSnsInfo(SnsVO snsVO);
	
	//등록
	public int insertSnsInfo(SnsVO snsVO);
	
	//수정
	public int updateSnsInfo(SnsVO snsVO);
	
	//삭제
	public int deleteSnsInfo(int snsVO);
	
	//sns 피드번호 생성
	public int selectSnsNum();
	
	//로그인정보 저장
	public int getUserNo();

}
