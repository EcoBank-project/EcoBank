package com.ecobank.app.sns.service;

import java.util.List;
import java.util.Map;

public interface SnsService {
	//전체 sns 조회
	public List<SnsVO> snsList();
	
	//단건 sns 조회
	public SnsVO snsInfo(SnsVO snsVO);
	
	//등록
	public int insertSns(SnsVO snsVO);
	
	//수정
	public Map<String, Object> updateSns(SnsVO snsVO);
	
	//삭제
	public int deleteSns(int SnsNo);
}
