package com.ecobank.app.chat.service;

import lombok.Data;

@Data
public class ChatFollowVO {
	private Integer followingId; //팔로잉 번호
	private String useId; 		 //팔로잉 아이디
	private String nickName;	 //팔로잉 닉네임
}
