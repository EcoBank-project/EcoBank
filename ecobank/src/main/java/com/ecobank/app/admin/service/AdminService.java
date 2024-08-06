package com.ecobank.app.admin.service;

import java.util.List;

public interface AdminService {
    
    // 유저 목록 조회
    List<UserVO> UserList();
    
    // 오늘 가입한 회원 수 조회
    int getcreaTeat();
    
    // 회원 상태 업데이트
    int updateUserState(String useId, String userState);
}
