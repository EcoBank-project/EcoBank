package com.ecobank.app.challenge.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.relational.core.sql.Like;

public interface ChallConfirmService {
	//인증 전체 조회 
	public List<ChallConfirmVO> confirmList(int challNo);
	
	//나의 인증 내역
	public MyConfirmDTO myConfirm(int userNo, int challNo);
	
	//나의 인증 상세
	public ChallConfirmVO myConfirmInfo(ChallConfirmVO challConfirmVO);
	
	//인증날짜 가져오려고(캘린더에 쓸)
	public List<Date> myCalendar(int userNo, int challNo);
	
	//상세이미지(+리뷰)
	public ChallVO reviewList(ChallVO challVO);
	
	//챌린지 참가 등록하기
	public int insertChallEnter(int userNo, int challNo);
	
	//챌린지 참가 여부 확인
	public boolean isUserParticipated(int userNo, int challNo);
	
	//챌린지 인증 등록
	public int confirmInsert(ChallConfirmVO challConfirmVO);
	
	//챌린지 참가 인증 등록 여부 확인
	public boolean isConfirmed(int userNo, int challNo);
	
	//챌린지 인증 삭제
	public int confirmDelete(int nowUserNo, int confirmNo);
	
	//인증 댓글 목록
	public List<ChallConfirmVO> confirmReplyList(ChallConfirmVO challConfirmVO);
	
	//인증 댓글 등록
	public int replyInsert(int userNo, ReplyVO replyVO);
	
	//인증 댓글 삭제
	public int replyDelete(int nowUserNo, int confirmReplyNo);
	
	//인증 개수
	public int getReplyCnt(int confirmNo);
	
	//인증 댓글 개수
	public int getConfirmNoFromReplyNo(int confirmReplyNo);
	
	//인증 좋아요 등록
	public LikeDTO confirmLikeInsert(ChallConfirmVO challConfirmVO);
	
	//인증 좋아요 상태
	public int confirmLikeStatus(int userNo, int confirmNo);
	
	//인증 신고 사유 목록
	public List<ChallConfirmVO> declareList();
	
	//인증 신고 등록
	public int declareInsert(ChallConfirmVO challConfirmVO);
}
