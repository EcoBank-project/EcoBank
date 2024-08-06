package com.ecobank.app.admin.service;

import java.util.Date;

import lombok.Data;


@Data
public class UserVO {
	
	private String useId;
	private String nickName;
	private  Date  creaTeat;
	private String tell;
	private String  resp;
	private String  country;
	private String  userState;
	

}
