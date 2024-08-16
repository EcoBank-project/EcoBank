package com.ecobank.app.home.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChallengeVO {
	private int challNo; 
    private String challTitle;
    private String challContent;
    private Date challStartat; 
    private Date challCloseat; 
    private String challState; 
    private String mainImg; 
    private String detailImg; 
    private int challScore; 
    private int viewCount; 
    private Date challCreateat; 
    private Date challUpdateat; 
    private int userNo;
    private String userCount;
    private int dDay;
}
