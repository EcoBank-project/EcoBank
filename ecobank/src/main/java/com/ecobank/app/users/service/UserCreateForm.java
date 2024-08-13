package com.ecobank.app.users.service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserCreateForm {
	@NotEmpty(message = "아이디는 필수 항목입니다.")
	@Email(message = "유효한 이메일 형식이어야 합니다.")
	private String useId;

	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	@Size(min = 6, message = "비밀번호는 최소 6자리 이상이어야 합니다.")
	private String password1;

	@NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
	private String password2;

	@Size(min = 4, max = 20)
	@NotEmpty(message = "닉네임은 필수 항목입니다.")
	private String nickName;

	@NotEmpty(message = "연락처는 필수 항목입니다.")
	private String tell;
}
