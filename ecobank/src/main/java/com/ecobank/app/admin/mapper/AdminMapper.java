package com.ecobank.app.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.admin.service.ChallDeclareVO;
import com.ecobank.app.admin.service.SnsDeclareVO;
import com.ecobank.app.admin.service.UserVO;

public interface AdminMapper {
    
    // 유저 목록 조회
    List<UserVO> userList();
    
    
    
    //가입한 회원수 조회
    int getusers();
    // 오늘 가입한 회원 수 조회
    int getcreaTeat();
    
    // 회원 상태 업데이트
    int updateUserState(@Param("useId") String useId, @Param("userState") String userState);
    
    //챌린지 신고 조회 
    List<ChallDeclareVO> ChallDeclareList();
    
    //챌린지 삭제
    int deleteChallDeclare (int confirmDeclareNo);
    
    //sns 신고 조회
    List<SnsDeclareVO> SnsDeclareList();
    
    //sns 댓글 신고 조회 
    List<SnsDeclareVO> SnsReplyDeclareList();
    
    //sns 상태 변환 업데이트
    int updatefeedState(@Param("feedNo") int feedNo, @Param("feedState") String feedState);
    
    //sns 신고 조회
    int snsDeclareDelete (int declareNo);
    
    int snsReplyDeclareDelete (int declareNo);
    
    
    
}
