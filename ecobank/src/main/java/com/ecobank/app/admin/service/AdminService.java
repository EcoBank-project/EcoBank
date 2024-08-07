package com.ecobank.app.admin.service;

import java.util.List;

public interface AdminService {
    
    // 유저 목록 조회
    public List<UserVO> UserList();
    
    //유저 회원수
    public int getusers();
    
    // 오늘 가입한 회원 수 조회
    public int getcreaTeat();
    
    // 회원 상태 업데이트
    public int updateUserState(String useId, String userState);
    
    // 챌린지 신고 목록 조회 
    public List<ChallDeclareVO> ChallDeclareList();
    
    // 챌린지 신고 삭제
    public void deleteChallDeclare(int confirmDeclareNo);
}
