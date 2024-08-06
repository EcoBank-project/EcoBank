package com.ecobank.app.users.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDTO {
	private String useId;

	private String password;

	private String nickName;

	private String tell;

	private Role role;

}
