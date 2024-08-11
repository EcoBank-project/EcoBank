package com.ecobank.app.users.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    
	public String findUserIdByPhoneNumber(@Param("phoneNumber") String phoneNumber);
    
    public int updatePassword(@Param("useId") String useId, @Param("encodedPassword") String encodedPassword);
}