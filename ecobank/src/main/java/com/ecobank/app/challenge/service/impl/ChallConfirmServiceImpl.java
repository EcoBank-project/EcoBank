package com.ecobank.app.challenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.challenge.mapper.ChallConfirmMapper;
import com.ecobank.app.challenge.service.ChallCofirmService;
import com.ecobank.app.challenge.service.ChallConfirmVO;

@Service
public class ChallConfirmServiceImpl implements ChallCofirmService{
	private ChallConfirmMapper challConfirmMapper;
	
	@Autowired
	public ChallConfirmServiceImpl(ChallConfirmMapper challConfirmMapper) {
		this.challConfirmMapper = challConfirmMapper;
	}

	@Override
	public List<ChallConfirmVO> confirmList(ChallConfirmVO chalConfirmVO) {
		return challConfirmMapper.selectConfirmAll(chalConfirmVO);
	}
}
