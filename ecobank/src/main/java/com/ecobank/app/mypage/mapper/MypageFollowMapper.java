package com.ecobank.app.mypage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ecobank.app.admin.service.UserVO;

@Mapper
public interface MypageFollowMapper {
    // 팔로잉 목록 쿼리
    List<UserVO> getFollowingList(@Param("userNo") int userNo);

    // 팔로워 목록 쿼리
    List<UserVO> getFollowerList(@Param("userNo") int userNo);
}
