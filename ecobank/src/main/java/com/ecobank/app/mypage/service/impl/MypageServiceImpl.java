package com.ecobank.app.mypage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.mypage.mapper.MypageChallMapper;
import com.ecobank.app.mypage.mapper.MypageProfileMapper;
import com.ecobank.app.mypage.service.MypageChallVO;
import com.ecobank.app.mypage.service.MypageService;

@Service
public class MypageServiceImpl implements MypageService{

	@Autowired
	MypageChallMapper challMapper;
	
	@Autowired
	MypageProfileMapper profileMapper;
	
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

	@Override
	public void updateProfileImage(Integer userNo, String fileName) {
        Map<String, Object> params = new HashMap<>();
        params.put("userNo", userNo);
        params.put("profileImg", fileName);

        profileMapper.UpdateUserProfile(params);		
	}

}
