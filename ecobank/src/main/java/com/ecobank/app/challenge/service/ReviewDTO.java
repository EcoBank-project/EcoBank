package com.ecobank.app.challenge.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ReviewDTO {
	//챌린지 후기
	private int reviewNo;			//챌린지 후기 번호
	private String reviewContent;	//챌린지 후기 내용
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date reviewCreateAt;	//챌린지 후기 등록 일자
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date reviewUpdateAt;	//챌린지 후기 수정 일자
	private int reviewStar;			//챌린지 후기 별점
	
	private Integer challNo; 		//챌린지 번호
	private int userNo;				//회원 번호
	private int confirmNo;			//챌린지 인증 번호
}
