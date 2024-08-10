package com.ecobank.app.admin.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChallDeclareVO {
		private int confirmDeclareNo; 			//챌린지 인증 신고 번호
		private String confirmDeclareType;  	//챌린지 인증 신고 분류
		private Date   confirmDeclareat;  		//챌린지 인증 신고 날짜
		private int confirmUserNo;  			//신고한 회원 번호
		private int confirmNo; 	                //인증 번호
}
