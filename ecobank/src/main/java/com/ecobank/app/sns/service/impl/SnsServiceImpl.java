package com.ecobank.app.sns.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.sns.mapper.SnsMapper;
import com.ecobank.app.sns.service.SnsService;
import com.ecobank.app.sns.service.SnsVO;

@Service
public class SnsServiceImpl implements SnsService{
	
	private SnsMapper snsMapper;
	
	@Autowired
	SnsServiceImpl(SnsMapper snsMapper){
		this.snsMapper = snsMapper;
	}
	//전체조회
	@Override
	public List<SnsVO> snsList() {
		return snsMapper.selectSnsAll();
	}
	
	//단건조회
	@Override
	public SnsVO snsInfo(SnsVO snsVO) {
		return snsMapper.selectSnsInfo(snsVO);
	}
	
	//등록
	@Override
	public int insertSns(SnsVO snsVO) {
		snsVO.setFeedCreateAt(new Date());
		int result = snsMapper.insertSnsInfo(snsVO);
		return result == 1 ? snsVO.getFeedNo() : -1;
	}
	
	//수정
	@Override
	public Map<String, Object> updateSns(SnsVO snsVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = snsMapper.updateSnsInfo(snsVO);
		if(result == 1) {
			isSuccessed = true;
		}
		
		map.put("result", isSuccessed); //map에 결과를 넣어줌(put)
		map.put("target", snsVO);
		
		return map;
	}
	
	//삭제
	@Override
	public int deleteSns(int SnsNo) {
		return snsMapper.deleteSnsInfo(SnsNo);
	}
	
	//번호 생성
	@Override
	public int selectSnsNum() {
		return snsMapper.selectSnsNum();
	}

}
