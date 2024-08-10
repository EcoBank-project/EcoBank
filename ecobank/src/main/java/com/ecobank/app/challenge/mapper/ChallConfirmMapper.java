package com.ecobank.app.challenge.mapper;

import java.util.List;

import com.ecobank.app.challenge.service.ChallConfirmVO;

public interface ChallConfirmMapper {
	//인증 전체 조회 
	public List<ChallConfirmVO> selectConfirmAll(ChallConfirmVO chalConfirmVO);
	
	//인증 단건 조회
	public ChallConfirmVO selectConfirmInfo(ChallConfirmVO chalConfirmVO);
}
