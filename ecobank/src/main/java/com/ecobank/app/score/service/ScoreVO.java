package com.ecobank.app.score.service;

import java.util.Date;

import lombok.Data;

@Data
public class ScoreVO {
	//점수 
	private int scoreNo;			//점수번호
	private int score;				//점수
	private Date scoreAt;			//획득 일자
	private String confirmCount;	//인증 횟수
	private int challNo;			//챌린지 번호
	private int userNo;				//회원번호
	
	//사용 점수
	private int useScoreNo;			//사용 점수 번호
	private int useScore;			//사용 점수
	private Date useAt;				//사용 일자
}
