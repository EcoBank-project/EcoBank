package com.ecobank.app.QnA.service;

import java.sql.Date;

import lombok.Data;

@Data
public class QnaVO {
	private int qnaNo;					//문의번호
	private String qnaContent;  		//문의 내용
	private String qnaTitle;  			//문의 제목
	private Date qnaCreateat; 			//문의 작성일자
	private int userNo;   				//회원번호
	private int qnaReplyNo;    			//답글번호
	private String  qnaReplyContent;	//답글내용
	private Date qnaReplyat;			//답글 작성일자 
	private String replyStatus;			//답글 확인
	private boolean hasReply;
	private String publicStatus; 
}
