package com.ecobank.app.challenge.service;

import java.util.Date;
import java.util.List;

public interface ChallConfirmService {
	//인증 전체 조회 
	//public List<ChallConfirmVO> confirmList(int challNo);
	
	//나의 인증 내역
	public MyConfirmDTO myConfirm(int userNo, int challNo);
	
	//인증날짜 가져오려고(캘린더에 쓸)
	public List<Date> myCalendar(int userNo, int challNo);
	
	//상세이미지(+리뷰)
	public ChallVO reviewList(ChallVO challVO);
	
	//챌린지 참가 등록하기
	public int insertChallEnter(ChallVO challVO);
}
