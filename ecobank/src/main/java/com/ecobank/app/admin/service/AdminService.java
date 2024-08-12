package com.ecobank.app.admin.service;

import java.util.List;

public interface AdminService {
    
    // 유저 목록 조회
    List<UserVO> UserList();
    
    //유저 회원수
    int getusers();
    
    // 오늘 가입한 회원 수 조회
    int getcreaTeat();
    
    // 회원 상태 업데이트
    int updateUserState(String useId, String userState);
    
    // 챌린지 신고 목록 조회 
    List<ChallDeclareVO> ChallDeclareList();
    
    //sns 신고 관리
    List<SnsDeclareVO> SnsDeclareList();
    
    //sns 댓글 신고 관리
    List<SnsDeclareVO> SnsReplyDeclareList();
    
    int updatefeedState(int feedNo, String feedState);
    
    //sns피드 신고 횟수
    int getCountByFeedNo(int feedNo);
    //sns댓글 신고 횟수
    int getCountByReplyNo(int replyNo);
    //챌린지 신고 횟수
    int getCountBychallNos(int confirmNo);
    
    // 프로시저 호출
    void UpdateSnsState();

}
