
package com.ecobank.app.mypage.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface MypageService {
	List<MypageChallVO> getChallengeInfo(int userNo);
	
	List<MypageChallVO> getExitChallengeInfo(int userNo);

	boolean cancelChallenge(int userNo, int challNo);
}