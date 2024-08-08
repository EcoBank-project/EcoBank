package com.ecobank.app.sns.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SnsReplyVO {

	private Integer replyNo;		//댓글번호
	private String replyContent;    //댓글내용
	private Integer userNo;			//회원번호
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date replyCreateAt;		//댓글 작성 일자
	private Integer feedNo;			//피드번호

}
