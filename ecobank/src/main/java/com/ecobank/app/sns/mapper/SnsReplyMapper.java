package com.ecobank.app.sns.mapper;

import java.util.List;

import com.ecobank.app.sns.service.SnsReplyVO;
import com.ecobank.app.sns.service.SnsVO;

public interface SnsReplyMapper {

	//피드별 댓글 조회 
	public List<SnsReplyVO> selectSnsReplyInfo(SnsReplyVO snsReplyVO);
	
	//댓글 등록
	public int insertSnsReplyInfo(SnsReplyVO snsReplyVO);

	//댓글 개별 삭제
	public int deleteSnsReplyInfo(int replyNO);
	
	//좋아요 등록
	public int insertSnsLike(SnsReplyVO snsReplyVO);
	
	//좋아요 삭제
	public int deleteSnsLike(int snsVO);
	

}
