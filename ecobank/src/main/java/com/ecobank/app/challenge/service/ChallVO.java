package com.ecobank.app.challenge.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.ecobank.app.common.service.Criteria;

import lombok.Data;

@Data
public class ChallVO extends Criteria{
	//챌린지
	private Integer challNo; 		//챌린지 번호
	private int userNo;				//회원번호(=관리자 번호)
	private String challTitle;		//챌린지 제목
	private String keyword;		//검색때문에 추가
	private String challContent;	//챌린지 내용
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date challStartAt;		//챌린지 시작 일자
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date challCloseAt;		//챌린지 종료 일자
	private String challState;		//챌린지 상태
	private String mainImg;			//메인 이미지
	private String detailImg;		//상세 이미지
	private int challScore;			//챌린지 점수
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date challCreateAt;		//챌린지 등록 일자
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date challUpdateAt;		//챌린지 수정 일자
	
	private long daysUntilStart;  	//챌린지 시작까지 남은 D-Day
	
	private int confirmNo;			//인증번호
	
	//챌린지 좋아요
	private int challLikeNo;		//챌린지 좋아요 번호
	
	//챌린지 참가자
	private Date challEnterAt;		//챌린지 도전 일자
	
}
