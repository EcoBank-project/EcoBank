package com.ecobank.app.mypage.service;

import java.util.Date;

import lombok.Data;

@Data
public class MypageChallVO {
	private int challNo;
	private String challTitle;
	private String challContent;
	private Date challStartat;
	private Date challCloseat;
	private Date challEnterat;
	private String challState;
	private String mainImg;
	private int userNo;
	private int confirmNo;
	private double progressRate;
	private int rn;
		
}
