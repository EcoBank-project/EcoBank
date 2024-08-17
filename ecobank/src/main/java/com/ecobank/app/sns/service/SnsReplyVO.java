package com.ecobank.app.sns.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SnsReplyVO {

	private Integer replyNo;		//댓글번호
	private String replyContent;    //댓글내용
	private Integer userNo;			//회원번호
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date replyCreateAt;		//댓글 작성 일자
	private Integer feedNo;			//피드번호
	private String nickname;		//닉네임
	private Integer snsLikeNo;			//좋아요 번호

}
