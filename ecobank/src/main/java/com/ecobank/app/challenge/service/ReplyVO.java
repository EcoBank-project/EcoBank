package com.ecobank.app.challenge.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ReplyVO {
	//챌린지 인증 댓글
	private int confirmReplyNo;			//인증 댓글 번호
	private String confirmReplyContent;	//인증 댓글 내용
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date confirmReplyCreateAt;	//인증 댓글 작성 일자
	
	//챌린지 인증
	private Integer confirmNo;			//인증번호
	private int userNo;					//회원번호
}
