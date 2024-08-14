package com.ecobank.app.users.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    
	// 아이디 찾기(문자 본인인증)
	public String findUserIdByPhoneNumber(@Param("phoneNumber") String phoneNumber);
    
	// 비밀번호 재설정(이메일 인증)
    public int updatePassword(@Param("useId") String useId, @Param("encodedPassword") String encodedPassword);
    
    // 구글 회원가입 여부
    public String findUserInfoByUseId(@Param("useId") String useId);
}