package com.ecobank.app.sns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.sns.mapper.SnsMapper;
import com.ecobank.app.sns.service.SnsService;
import com.ecobank.app.sns.service.SnsVO;

@Service
public class SnsServiceImpl implements SnsService{
	
	@Autowired
	SnsMapper snsMapper;
	
	@Override
	public List<SnsVO> snsList() {
		return snsMapper.selectSnsAll();
	}

}
