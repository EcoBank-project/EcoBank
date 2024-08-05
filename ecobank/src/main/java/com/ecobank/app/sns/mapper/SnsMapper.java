package com.ecobank.app.sns.mapper;

import java.util.List;

import com.ecobank.app.sns.service.SnsVO;

public interface SnsMapper {
	
	//전체조회
	public List<SnsVO> selectSnsAll();
}
