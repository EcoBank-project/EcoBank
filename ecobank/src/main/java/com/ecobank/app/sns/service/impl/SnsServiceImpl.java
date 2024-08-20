package com.ecobank.app.sns.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.sns.mapper.SnsMapper;
import com.ecobank.app.sns.mapper.SnsReplyMapper;
import com.ecobank.app.sns.service.SnsService;
import com.ecobank.app.sns.service.SnsVO;
import com.ecobank.app.upload.mapper.FileMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SnsServiceImpl implements SnsService{
	
	private final SnsMapper snsMapper;
	private final SnsReplyMapper snsReplyMapper;
	private final FileMapper fileMapper;
	
	
	//전체조회
	@Override
	public List<SnsVO> snsList(SnsVO snsVO) {
		return snsMapper.selectSnsAll(snsVO);
	}
	
	//검색 조회
	@Override
	public List<SnsVO> snsSearch(String keyword, Integer userNo) {
		SnsVO snsVO = new SnsVO();
		if(keyword != null && keyword.charAt(0)=='#') {
			snsVO.setHashtag(keyword);
		} else if(keyword != null) {
			snsVO.setNickname(keyword);
		}
		snsVO.setUserNo(userNo);
		return snsMapper.searchSnsAll(snsVO);
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
		//댓글삭제
		snsMapper.deleteSnsReplyAll(SnsNo);
		//파일삭제
		fileMapper.deleteFileInfo(SnsNo);
		//sns지우기
		return snsMapper.deleteSnsInfo(SnsNo);
	}

	//세선로그인값
    public int getUserNo() {
        return snsMapper.getUserNo();
    }
    
    //신고사유목록
	@Override
	public List<SnsVO> snsDeclareList() {
		return snsMapper.selectSnsDeclare();
	}
	
	//신고등록
	@Override
	public int insertsnsDeclare(SnsVO snsVO) {
		snsVO.setDeclareat(new Date());
		int result = snsMapper.insertSnsDeclare(snsVO);
		return result == 1 ? snsVO.getDeclareNo() : -1;
		
	}
	
	//마이피드 조회
	@Override
	public List<SnsVO> mySns(SnsVO snsVO) {
		System.out.println("서비"+snsVO);
		return snsMapper.selectMySns(snsVO);
	}

	//마이피드 세부 조회
	@Override
	public SnsVO countMySns(SnsVO snsVO) {
		return snsMapper.countMySns(snsVO);
	}


    

}
