package com.ecobank.app.challenge.service;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class MyConfirmDTO {		//바구니
	private int userNo;			//회원 번호	
	private int challNo;		//챌린지 번호
	private int score;			//챌린지 점수
	private int confirmCount;	//인증 횟수
	private int remainCount; 	//남은 인증 횟수
}
