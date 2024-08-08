package com.ecobank.app.sns.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.sns.mapper.SnsReplyMapper;
import com.ecobank.app.sns.service.SnsReplyService;
import com.ecobank.app.sns.service.SnsReplyVO;

@Service
public class SnsReplyServiceImpl implements SnsReplyService{
	
	private SnsReplyMapper snsReplyMapper;
	
	@Autowired
	SnsReplyServiceImpl(SnsReplyMapper snsReplyMapper){
		this.snsReplyMapper = snsReplyMapper;
	}
	
	//댓글조회
	@Override
	public List<SnsReplyVO> snsReplyInfo(SnsReplyVO snsReplyVO) {
		return snsReplyMapper.selectSnsReplyInfo(snsReplyVO);
	}

	//등록
	@Override
	public int insertSnsReply(SnsReplyVO snsReplyVO) {
		snsReplyVO.setReplyCreateAt(new Date());
		int result = snsReplyMapper.insertSnsReplyInfo(snsReplyVO);
		return result == 1 ? snsReplyVO.getReplyNo() : -1;
	}
	
	//삭제
	@Override
	public int deleteSnsReply(int replyNo) {
		return snsReplyMapper.deleteSnsReplyInfo(replyNo);
	}
	
}