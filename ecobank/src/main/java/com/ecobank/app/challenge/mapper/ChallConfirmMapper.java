package com.ecobank.app.challenge.mapper;

import java.util.List;

import com.ecobank.app.challenge.service.ChallConfirmVO;

public interface ChallConfirmMapper {
	//인증 전체 조회 
	public List<ChallConfirmVO> selectConfirmAll();
	
	
}
