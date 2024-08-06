package com.ecobank.app.users.service;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserCreateForm {
	@NotEmpty(message = "아이디는 필수 항목입니다.")
	private String useId;

	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	private String password1;

	@NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
	private String password2;
	
	@Size(min = 3, max = 20)
	@NotEmpty(message = "닉네임은 필수 항목입니다.")
	private String nickName;
	
	@NotEmpty(message = "연락처는 필수 항목입니다.")
	private String tell;
}
