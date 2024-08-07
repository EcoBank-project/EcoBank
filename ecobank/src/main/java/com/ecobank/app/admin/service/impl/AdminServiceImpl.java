package com.ecobank.app.admin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.admin.mapper.AdminMapper;
import com.ecobank.app.admin.service.AdminService;
import com.ecobank.app.admin.service.ChallDeclareVO;
import com.ecobank.app.admin.service.UserVO;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    
    //유저 목록 조회
    @Override
    public List<UserVO> UserList() {
        return adminMapper.userList();
    }
    // 오늘 가입한 회원 수 조회
    @Override
    public int getcreaTeat() {
        return adminMapper.getcreaTeat();
    }
    // 회원 상태 업데이트 
    @Override
    public int updateUserState(String useId, String userState) {
        return adminMapper.updateUserState(useId, userState);
    }
    //챌린지 신고 목록 조회
	@Override
	public List<ChallDeclareVO> ChallDeclareList() {
		// TODO Auto-generated method stub
		return adminMapper.ChallDeclareList();
	}
	
	
}
