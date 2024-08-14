package com.ecobank.app.googleuser.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class GoogleUserInfoForm {

	@NotEmpty
	private String useId;
	
	@Size(min = 4, max = 20)
	@NotEmpty(message = "닉네임은 필수 항목입니다.")
	private String nickName;
	
	@NotEmpty(message = "연락처는 필수 항목입니다.")
	private String tell;
	
	
}
