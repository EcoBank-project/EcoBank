package com.ecobank.app.challenge.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChallVO {
	//챌린지
	private Integer challNo; 		//챌린지 번호
	private int user_no;			//회원번호(=관리자 번호)
	private String challTitle;		//챌린지 제목
	private String challContent;	//챌린지 내용
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date challStartAt;		//챌린지 시작 일자
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date challCloseAt;		//챌린지 종료 일자
	private String challState;		//챌린지 상태
	private String mainImg;			//메인 이미지
	private String detailImg;		//상세 이미지
	private int challScore;			//챌린지 점수
	private int viewCount;			//조회수
	private Date challCreateAt;		//챌린지 등록 일자
	private Date challUpdateAt;		//챌린지 수정 일자
	
	//챌린지 좋아요
	private int challLikeNo;		//챌린지 좋아요 번호
	
	//챌린지 참가자
	private Date challEnterAt;		//챌린지 도전 일자
	
	//챌린지 후기(얘 위치..)
	private int reviewNo;			//챌린지 후기 번호
	private String reviewContent;	//챌린지 후기 내용
	private Date reviewCreateAt;	//챌린지 후기 등록 일자
	private Date reviewUpdateAt;	//챌린지 후기 수정 일자
	private int reviewStar;			//챌린지 후기 별점
	
	//점수 
//	private int scoreNo;			//점수번호
//	private int score;				//점수
//	private String confirmCount;	//인증 횟수
//	private Date scoreAt;			//획득 일자
//	
//	//사용 점수
//	private int useScoreNo;			//사용 점수 번호
//	private int useScore;			//사용 점수
//	private Date useAt;				//사용 일자
}
