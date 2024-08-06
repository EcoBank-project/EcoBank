package com.ecobank.app.admin.service;

import java.util.Date;

import lombok.Data;


@Data
public class UserVO {
	
	private String useId;
	private String nickName;
	private String password;
	private  Date  creaTeat;
	private String tell;
	private String  roleName;
	private String  country;
	private String  userState;
	

}
