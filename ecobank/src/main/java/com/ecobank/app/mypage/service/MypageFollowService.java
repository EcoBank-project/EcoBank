package com.ecobank.app.mypage.service;

import java.util.List;

import com.ecobank.app.admin.service.UserVO;

public interface MypageFollowService {
	
	public List<UserVO> getFollowingList(int userNo);
	
	public List<UserVO> getFollowerList(int userNo);
	
}
