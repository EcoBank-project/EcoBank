package com.ecobank.app.intro.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.intro.service.CarbonVO;

public interface CarbonMapper {
	// 전체 조회
	public List<CarbonVO> selectCarbonAll();
}
