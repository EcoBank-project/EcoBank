package com.ecobank.app.sns.service;

import java.util.List;
import java.util.Map;

public interface SnsReplyService {
	
	//피드별 sns 조회
	public List<SnsReplyVO> snsReplyInfo(SnsReplyVO snsReplyVO);
	
	//등록
	public int insertSnsReply(SnsReplyVO snsReplyVO);

	//삭제
	public int deleteSnsReply(int replyNo);
	
	//좋아요 등록
	public int insertSnsLike(SnsReplyVO snsReplyVO);
		
	//좋아요 삭제
	public int deleteSnsLike(SnsReplyVO snsReplyVO);
	
	//팔로워 조회
	public List<SnsReplyVO> followerInfo(SnsReplyVO snsReplyVO);
	
	//팔로우 조회
	public List<SnsReplyVO> followingInfo(SnsReplyVO snsReplyVO);
	
	//팔로우 등록
	public int insertFollow(SnsReplyVO snsReplyVO);
		
	//팔로우 삭제
	public int deleteFollow(SnsReplyVO snsReplyVO);
	
	//차단 등록
	public int insertBlock(SnsReplyVO snsReplyVO);
		
	//차단 삭제
	public int deleteBlock(SnsReplyVO snsReplyVO);
}
