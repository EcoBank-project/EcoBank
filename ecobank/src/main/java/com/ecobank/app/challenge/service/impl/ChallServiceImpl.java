package com.ecobank.app.challenge.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.challenge.mapper.ChallMapper;
import com.ecobank.app.challenge.service.ChallService;
import com.ecobank.app.challenge.service.ChallVO;
import com.ecobank.app.challenge.service.ScoreVO;
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
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = challMapper.updateChallInfo(challVO);
		if(result > 0) { //수정되었으면 
			isSuccessed = true; //성공
		}
		
		map.put("result", isSuccessed); //수정되었을때 true,실패하면 false 반환
		map.put("target", challVO); //수정된 내용
		
		return map;
	}

	@Override
	public int challDelete(int challNo) {
		return challMapper.deleteChallInfo(challNo);
	}
	//점수 목록 조회
	@Override
	public List<Map<String, Object>> scoreList() {
		return challMapper.selectScoreAll();
	}

	@Override
	public int countChallengesByState(String challState) {
		return challMapper.countAllChallenges(challState);
	}

//	@Override
//	public int selectChallNum() {
//		return challMapper.selectChallNum();
//	}
	
}
