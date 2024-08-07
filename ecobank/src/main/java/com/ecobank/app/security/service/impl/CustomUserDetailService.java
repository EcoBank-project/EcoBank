package com.ecobank.app.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecobank.app.admin.service.UserVO;
import com.ecobank.app.security.mapper.UserMapper;
import com.ecobank.app.security.service.LoginUserVO;

@Service
public class CustomUserDetailService implements UserDetailsService{
	private UserMapper userMapper;

	@Autowired
	public CustomUserDetailService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override // 인증을 하기위한 정보 조회
	public UserDetails loadUserByUsername(String useId) throws UsernameNotFoundException {
		
		
		UserVO userVO = userMapper.getUserInfo(useId);
		System.out.println(userVO);
		
		if( userVO == null ) { // 유저 정보가 없을 시
			throw new UsernameNotFoundException(useId);
		}
		
		return new LoginUserVO(userVO);
	}
	
}

