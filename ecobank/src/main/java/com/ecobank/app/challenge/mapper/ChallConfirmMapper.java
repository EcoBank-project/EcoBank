package com.ecobank.app.challenge.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.challenge.service.ChallConfirmVO;
import com.ecobank.app.challenge.service.ChallVO;
import com.ecobank.app.challenge.service.MyConfirmDTO;
import com.ecobank.app.challenge.service.ReplyVO;

public interface ChallConfirmMapper {
	//인증 전체 조회 - 챌린지 상세에서 다른 참가자 인증 내역
	public List<ChallConfirmVO> selectConfirmAll(int challNo);
	
	//인증 단건 조회 - 챌린지 상세에서 다른 참가자 인증 상세
	public ChallConfirmVO selectConfirmInfo(ChallConfirmVO challConfirmVO);
	
	//나의 인증 내역
	public MyConfirmDTO myConfirm(int userNo, int challNo); 
	
	//나의 인증 상세
	public ChallConfirmVO myConfirmDetail(ChallConfirmVO challConfirmVO);
	
	//인증날짜 가져오기(남은 인증 횟수가져오려고)
	public List<Date> getConfirmDate(@Param("userNo") int userNo, @Param("challNo") int challNo);
	
	//인증 횟수 가져오려고(+1할지 말지)
	public int getConfirmCnt(int challNo);
	
	//상세이미지(+리뷰)
	public ChallVO reviewList(ChallVO challVO);
	
	//챌린지 참가 등록하기
	public int challEnterInsert(@Param("userNo") int userNo, @Param("challNo") int challNo);
	
	//챌린지 참가 여부 확인(결과가 있으면 1/ 참여한적 없으면 0)
	public int enterStatus(@Param("userNo") int userNo, @Param("challNo") int challNo);
	
	//챌린지 인증 등록(userNo, confirmNo, confirmContent, file)
	public int insertConfirmInfo(ChallConfirmVO challConfirmVO);
	
	//챌린지 인증글 삭제하려고 인증 유저 번호 가져오기 
	public int findUserNoByConfirmNo(int confirmNo);
	
	//챌린지 인증 삭제
	public int deleteConfirmInfo(int confirmNo);
	
	//챌린지 인증 여부 확인(결과가 있으면 1/ 참여한적 없으면 0)
	public int confirmStatus(@Param("userNo") int userNo, @Param("challNo") int challNo);
	
	//인증 댓글 목록
	public List<ChallConfirmVO> selectReplyList(ChallConfirmVO challConfirmVO);
	
	//인증 댓글 등록
	public int insertReply(ReplyVO replyVO);
	
	//인증 댓글 삭제하려고 인증 유저 번호 가져오기
	public int findUserNoByReplyNo(int confirmReplyNo);
	
	//인증 댓글 삭제
	public int deleteReply(int confirmReplyNo);
	
	//인증 좋아요 등록
	public int insertConfirmLike(ChallConfirmVO challConfirmVO);
	
	//인증 좋아요 삭제
	public int deleteConfirmLike(int userNo, int confirmNo);
	
	//인증 좋아요 했는지 안했는지 여부
	public int confirmLikeStatus(int userNo, int confirmNo);
	
	//인증 좋아요 전체 개수
	public int likeTotalCnt(int confirmNo);
	
}
