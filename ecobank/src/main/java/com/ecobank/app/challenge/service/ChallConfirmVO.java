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
	private int confirmReplyNo;			//
	private String confirmReplyContent;
	private Date confirmReplyCreateAt;
}
