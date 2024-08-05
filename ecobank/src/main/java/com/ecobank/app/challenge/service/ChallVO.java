package com.ecobank.app.challenge.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChallVO {
	private Integer challNo; 		//챌린지번호
	private int user_no;			//회원번호
	private String challTitle;		//챌린지 제목
	private String challContent;	//챌린지 내용
	private Date challStartAt;		//챌린지 시작 일자
	private Date challCloseAt;		//챌린지 종료 일자
	private String challState;		//챌린지 상태
	private String mainImg;			//메인 이미지
	private String detailImg;		//상세 이미지
	private int challScore;			//챌린지 점수
	private int viewCount;			//조회수
	private Date challCreateAt;		//챌린지 등록 일자
	private Date challUpdateAt;		//챌린지 수정 일자
}
