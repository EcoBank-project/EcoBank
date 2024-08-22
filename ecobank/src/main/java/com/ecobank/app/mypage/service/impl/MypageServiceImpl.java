package com.ecobank.app.mypage.service.impl;

import java.util.List;

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
}
