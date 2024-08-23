package com.ecobank.app.mypage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ecobank.app.mypage.service.MypageChallVO;

@Mapper
public interface MypageChallMapper {
	
    List<MypageChallVO> getChallengeInfo(int userNo);

    int cancleChall(@Param("userNo") Integer userNo, @Param("challNo") Integer challNo);
    
    List<MypageChallVO> getExitChallengeInfo(int userNo);
}
