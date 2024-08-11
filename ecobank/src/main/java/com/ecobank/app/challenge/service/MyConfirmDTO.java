package com.ecobank.app.challenge.service;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class MyConfirmDTO {
	private int userNo;
	private int challNo;
	private int score;
	private int confirmCount;
	private int remainCount; //남은 인증 횟수
}
