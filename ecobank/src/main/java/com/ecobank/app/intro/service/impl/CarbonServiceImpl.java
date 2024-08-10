package com.ecobank.app.intro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.intro.mapper.CarbonMapper;
import com.ecobank.app.intro.service.CarbonService;
import com.ecobank.app.intro.service.CarbonVO;

@Service
public class CarbonServiceImpl implements CarbonService {
	private CarbonMapper carbMapper;
	
	@Autowired
	public CarbonServiceImpl(CarbonMapper carbMapper) {
		this.carbMapper = carbMapper;
	}
	
	
	
	@Override
	public List<CarbonVO> CarbonList() {
		// TODO Auto-generated method stub
		return carbMapper.selectCarbonAll();
	}
}
