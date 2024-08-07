package com.ecobank.app.security.mapper;

import com.ecobank.app.admin.service.UserVO;

public interface UserMapper {
	
	public UserVO getUserInfo(String useId);

}
