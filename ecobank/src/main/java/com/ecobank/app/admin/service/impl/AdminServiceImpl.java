package com.ecobank.app.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.admin.mapper.AdminMapper;
import com.ecobank.app.admin.service.AdminService;
import com.ecobank.app.admin.service.ChallDeclareVO;
import com.ecobank.app.admin.service.SnsDeclareVO;
import com.ecobank.app.admin.service.UserVO;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    
    // 유저 목록 조회
    @Override
    public List<UserVO> UserList() {
        return adminMapper.userList();
    }
    //총 회원 수 
    @Override
	public int getusers() {
		// TODO Auto-generated method stub
		return adminMapper.getusers();
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
    
    // 챌린지 신고 목록 조회
    @Override
    public List<ChallDeclareVO> ChallDeclareList() {
        return adminMapper.ChallDeclareList();
    }
    
    // 챌린지 신고 삭제
    @Override
    public void deleteChallDeclare(int confirmDeclareNo) {
        adminMapper.deleteChallDeclare(confirmDeclareNo);
    }
    //sns 댓글 신고 조회 
	@Override
	public List<SnsDeclareVO> SnsReplyDeclareList() {
		return adminMapper.SnsReplyDeclareList();
	}
	//sns 신고 조회;
	@Override
	public List<SnsDeclareVO> SnsDeclareList() {
		return adminMapper.SnsDeclareList();
	}
	//sns 신고 삭제;
	@Override
	public void snsDeclareDelete(int declareNo) {
		adminMapper.snsDeclareDelete(declareNo);
	}
	//sns 신고 삭제
	@Override
	public void snsReplyDeclareDelete(int declareNo) {
		adminMapper.snsReplyDeclareDelete(declareNo);
	}
	@Override
	public int updatefeedState(int feedNo, String feedState) {
		// TODO Auto-generated method stub
		return adminMapper.updatefeedState(feedNo, feedState);
	}
	@Override
	public int getCountByFeedNo(int feedNo) {
		// TODO Auto-generated method stub
		 return adminMapper.getCountByFeedNo(feedNo);
	}
	@Override
	public int getCountByReplyNo(int replyNo) {
		// TODO Auto-generated method stub
		return adminMapper.getCountByReplyNo(replyNo);
	}
	
	
	 
	
	
}
