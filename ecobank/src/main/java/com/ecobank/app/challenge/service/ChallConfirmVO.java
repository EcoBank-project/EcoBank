package com.ecobank.app.challenge.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChallConfirmVO {
	//챌린지 인증
	private Integer confirmNo;			//인증번호
	private int userNo;					//회원번호
	private String confirmContent;		//인증내용
	private Date confirmCreateAt;		//인증 작성 일자
	
	//챌린지 인증 댓글
	private int confirmReplyNo;			//인증 댓글 번호
	private String confirmReplyContent;	//인증 댓글 내용
	private Date confirmReplyCreateAt;	//인증 댓글 작성 일자
	
	//챌린지 인증 좋아요
	private int confirmLikeNo;			//인증 좋아요
	
	//챌린지 인증 신고
	private int confirmDeclareNo;		//인증 신고 번호
	private String confirmDeclareType;	//인증 신고 분류
	private Date confirmDeclareAt;		//인증 신고 날짜
	//private int confirmUserNo;		//신고한 회원 번호(회원번호랑 같지않나..? 굳이..?)
}
