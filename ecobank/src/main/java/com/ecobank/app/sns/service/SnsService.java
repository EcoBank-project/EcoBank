package com.ecobank.app.sns.service;

import java.util.List;
import java.util.Map;

public interface SnsService {
	//전체 sns 조회
	public List<SnsVO> snsList(SnsVO snsVO);
	
	//sns 검색 조회
	public List<SnsVO> snsSearch(String keyword, Integer userNo);
	
	//단건 sns 조회
	public SnsVO snsInfo(SnsVO snsVO);
	
	//등록
	public int insertSns(SnsVO snsVO);
	
	//수정
	public Map<String, Object> updateSns(SnsVO snsVO);
	
	//삭제
	public int deleteSns(int SnsNo);
	
	//로그인정보 저장
	public int getUserNo();
	
	// sns신고 사유 조회
	public List<SnsVO> snsDeclareList();

	// sns신고 등록
	public int insertsnsDeclare(SnsVO snsVO);
	
	//마이피드 조회
	public List<SnsVO> mySns(SnsVO snsVO);
	
	//마이피드 세부 정보 조회
	public SnsVO countMySns(SnsVO snsVO);
	
	//팔로우 여부 확인
	public int selectFollow(int userNo, int otherNo);
	
}
