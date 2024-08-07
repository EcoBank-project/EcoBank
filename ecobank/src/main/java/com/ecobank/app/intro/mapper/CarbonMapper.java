package com.ecobank.app.intro.mapper;

import java.util.List;

import com.ecobank.app.intro.service.CarbonVO;

public interface CarbonMapper {
	// 전체 조회
	public List<CarbonVO> selectCarbonAll();
}
