package com.ecobank.app.chat.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChatMessageVO {
	private Integer msgNo;		//메시지 번호
	private String msgContent; 	//채팅 내용
	private String nickName;	//회원 닉네임
	private String msgType;		//채팅 타입	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date msgSendTime;	//채팅 시간
	private String forMatTime;	//채팅 시간 포맷
	private Integer userNo;		//회원 번호
	private Integer chatNo;		//채팅방 번호
	private String chatType;	//채팅방 타입
	
	private List<Integer> userNos;	//사용자 번호
	private String profileImg;
	
	private String msgFilePath;
	private String msgFileType;
}
