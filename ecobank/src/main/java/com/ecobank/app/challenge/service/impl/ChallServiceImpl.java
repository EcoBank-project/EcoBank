package com.ecobank.app.challenge.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.challenge.mapper.ChallMapper;
import com.ecobank.app.challenge.service.ChallService;
import com.ecobank.app.challenge.service.ChallVO;
@Service
public class ChallServiceImpl implements ChallService{
	private ChallMapper challMapper;
	
	@Autowired
	public ChallServiceImpl(ChallMapper challMapper) {
		this.challMapper = challMapper;
	}
	
	//전체조회
	@Override
	public List<ChallVO> challList() {
		return challMapper.selectChallAll();
	}
	
	//단건조회
	@Override
	public ChallVO challInfo(ChallVO challVO) {
		return challMapper.selectChallInfo(challVO);
	}

	//챌린지 등록
	@Override
	public int challInsert(ChallVO challVO) {
		int result = challMapper.insertChallInfo(challVO);
		return result == 1 ? challVO.getChallNo() : -1;
	}

	//챌린지 수정
	@Override
	public Map<String, Object> challUpdate(ChallVO challVO) {
		return null;
	}
	
}
