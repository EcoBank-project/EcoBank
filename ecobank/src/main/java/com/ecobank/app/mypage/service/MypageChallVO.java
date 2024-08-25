package com.ecobank.app.mypage.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MypageChallVO {
	private int challNo;
	private String challTitle;
	private String challContent;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date challStartat;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date challCloseat;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date challEnterat;
	private String challState;
	private String mainImg;
	private int userNo;
	private int confirmNo;
	private double progressRate;
	private int confirmCount;
	private int score;
	private int rn;
	private int dDay;
}
