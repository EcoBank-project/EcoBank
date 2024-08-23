package com.ecobank.app.mypage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.admin.service.UserVO;
import com.ecobank.app.mypage.mapper.MypageFollowMapper;
import com.ecobank.app.mypage.service.MypageFollowService;

@Service
public class MypageFollowServiceImpl implements MypageFollowService{
    @Autowired
    private MypageFollowMapper followMapper;

    public List<UserVO> getFollowingList(int userNo) {
        return followMapper.getFollowingList(userNo);
    }

    public List<UserVO> getFollowerList(int userNo) {
        return followMapper.getFollowerList(userNo);
    }
}
