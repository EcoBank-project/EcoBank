package com.ecobank.app.chat.service;

import java.util.List;

// 사용자 기준
public interface ChatService {
	//로그인한 회원 전체 채팅방 조회
	public List<ChatRoomVO> chatRoomList(Integer chatId, String nickName);
	//로그인한 회원 채팅방 정보 조회
	public ChatRoomVO chatRoomInfo();
	//채팅방 채팅로그 기록 조회
	public List<ChatMessageVO> chatMessageList(Integer chatNo);
	//채팅방 채팅로그 기록 저장
	public ChatMessageVO ChatMessageInsert(ChatMessageVO chatMessageVO);
	//채팅방 만들기
	public int ChatRoomInsert(ChatRoomDTO chatRoom, Integer userNo);
	//채팅방 타입 조회
	public String chatRoomType(Integer chatNo);
	//채팅방 참여자
	public int ChatUserInsert(Integer chatNo, Integer userNo);
	//채팅방 참여자 아이디
	public List<String> ChatUserList(Integer chatNo);
	//팔로우 목록
	public List<ChatFollowVO> chatFollowList(Integer userNo);
}
