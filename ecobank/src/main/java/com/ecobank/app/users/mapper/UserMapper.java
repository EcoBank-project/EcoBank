package com.ecobank.app.users.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

	// 본인인증 (핸드폰번호 같은 지 확인)
    @Select("SELECT use_id FROM users WHERE tell = #{phoneNumber}")
    String findUserIdByPhoneNumber(String phoneNumber);
    
}