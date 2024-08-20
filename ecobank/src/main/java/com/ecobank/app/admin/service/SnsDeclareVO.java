package com.ecobank.app.admin.service;

import java.util.Date;

import lombok.Data;

@Data
public class SnsDeclareVO {
					private int	 declareNo;				 //sns 신고 번호
					private Date declareat;				 //sns 신고 일자 
					private int	 confirmUserNo;			 //신고한 회원 번호
					private String	 declareCode;		 //sns 신고이유
					private int	 replyNo;	 			 //댓글 번호
					private int  feedNo;				 //피드 번호
					private int replyContent;  			 //댓글내용
					private Date replyCreateat;			 //댓글 작성일자
					private int userNo; 				 //유저번호
					private String snsSituation; 
					
}
