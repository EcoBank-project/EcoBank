package com.ecobank.app.challenge.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChallConfirmVO {
	//챌린지 인증
	private Integer confirmNo;			//인증번호
	private int userNo;					//회원번호
	private Integer challNo;			//챌린지번호
	private String confirmContent;		//인증내용
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date confirmCreateAt;		//인증 작성 일자
	private String nickname;      		//닉네임
	
	//챌린지 인증 댓글
	private int confirmReplyNo;			//인증 댓글 번호
	private String confirmReplyContent;	//인증 댓글 내용
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date confirmReplyCreateAt;	//인증 댓글 작성 일자
	private Integer cntReply;			//댓글 갯수
	
	//챌린지 인증 좋아요
	private int confirmLikeNo;			//인증 좋아요 번호
	private Integer cntLike;			//좋아요 갯수
	
	//챌린지 인증 신고
	private int confirmDeclareNo;		//인증 신고 번호
	private String confirmDeclareType;	//인증 신고 분류
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date confirmDeclareAt;		//인증 신고 날짜
	
	private String fileName;			//파일이름
	private String filePath;			//파일경로
	private String fileCode;			//파일 분류 코드
	private int fileCodeNo;				//파일 분류 코드 번호 = 챌린지 인증 번호 
}
