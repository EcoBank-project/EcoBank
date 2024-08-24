package com.ecobank.app.mypage.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MypageProfileMapper {
	
	void UpdateUserProfile(Map<String, Object> params);

	
}
