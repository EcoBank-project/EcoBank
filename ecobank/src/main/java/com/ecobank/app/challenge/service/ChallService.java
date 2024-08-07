package com.ecobank.app.challenge.service;

import java.util.List;
import java.util.Map;

public interface ChallService {
	//챌린지 전체 조회
	public List<ChallVO> challList();
	
	//챌린지 단건 조회
	public ChallVO challInfo(ChallVO challVO);
	
	//챌린지 등록
	public int challInsert(ChallVO challVO);
	
	//챌린지 수정
	public Map<String, Object> challUpdate(ChallVO challVO);
	
}
