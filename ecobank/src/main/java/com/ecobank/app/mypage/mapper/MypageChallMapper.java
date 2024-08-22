package com.ecobank.app.mypage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ecobank.app.mypage.service.MypageChallVO;

@Mapper
public interface MypageChallMapper {
	
    List<MypageChallVO> getChallengeInfo(int userNo);

}
