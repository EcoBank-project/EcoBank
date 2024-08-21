package com.ecobank.app.sns.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.sns.mapper.SnsReplyMapper;
import com.ecobank.app.sns.service.SnsReplyService;
import com.ecobank.app.sns.service.SnsReplyVO;
import com.ecobank.app.sns.service.SnsVO;

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
	
	//좋아요 등록
	@Override
	public int insertSnsLike(SnsReplyVO snsReplyVO) {
		int result = snsReplyMapper.insertSnsLike(snsReplyVO);
		return result == 1 ? snsReplyVO.getSnsLikeNo() : -1;
	}

	//좋아요 삭제
	@Override
	public int deleteSnsLike(SnsReplyVO snsReplyVO) {
		return snsReplyMapper.deleteSnsLike(snsReplyVO);
	}
	

	//팔로우 등록
	@Override
	public int insertFollow(SnsReplyVO snsReplyVO) {		
		int result = snsReplyMapper.insertFollow(snsReplyVO);
		return result == 1 ? snsReplyVO.getFollowNo() : -1;
	}
	
	//팔로우 삭제
	@Override
	public int deleteFollow(SnsReplyVO snsReplyVO) {
		return snsReplyMapper.deleteFollow(snsReplyVO);
	}

	//차단 등록
	@Override
	public int insertBlock(SnsReplyVO snsReplyVO) {
		int result = snsReplyMapper.insertBlock(snsReplyVO);
		return result == 1 ? snsReplyVO.getSnsBlockNo() : -1;
	}

	//차단 삭제
	@Override
	public int deleteBlock(SnsReplyVO snsReplyVO) {
		return snsReplyMapper.deleteBlock(snsReplyVO);
	}
	

}
