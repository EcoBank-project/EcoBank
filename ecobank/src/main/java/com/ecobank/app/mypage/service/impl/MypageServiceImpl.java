package com.ecobank.app.mypage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.mypage.mapper.MypageChallMapper;
import com.ecobank.app.mypage.service.MypageChallVO;
import com.ecobank.app.mypage.service.MypageService;

@Service
public class MypageServiceImpl implements MypageService{

	@Autowired
	MypageChallMapper challMapper;
	
    public List<MypageChallVO> getChallengeInfo(int userNo) {
        return challMapper.getChallengeInfo(userNo);
    }

    @Override
    public boolean cancelChallenge(int userNo, int challNo) {
        int result = challMapper.cancleChall(userNo, challNo);
        return result > 0; // 성공적으로 삭제된 경우 true 반환
    }

	@Override
	public List<MypageChallVO> getExitChallengeInfo(int userNo) {
		return challMapper.getExitChallengeInfo(userNo);
	}
}
