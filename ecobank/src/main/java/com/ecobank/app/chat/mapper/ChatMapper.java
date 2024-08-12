package com.ecobank.app.chat.mapper;

import java.util.List;

import com.ecobank.app.chat.service.ChatFollowVO;
import com.ecobank.app.chat.service.ChatMessageDTO;
import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatRoomVO;

// 서버 기준
public interface ChatMapper {
	// 로그인한 회원 채팅방 전체 조회
	public List<ChatRoomVO> selectChatRoomAll(Integer userNo);
	// 로그인한 회원 채팅방 정보 조회
	public ChatRoomVO selectChatRoomInfo(ChatRoomVO chatRoomVO);
	// 채팅방 채팅로그 조회
	public List<ChatMessageDTO> selectChatMessage(Integer chatNo);
	// 채팅방 채팅로그 기록
	public int insertChatMessage(ChatMessageVO chatMessageVO);
	// 채팅방 만들기
	public int insertChatRoom(ChatRoomVO chatRoomVO);
	// 팔로우 목록
	public List<ChatFollowVO> selectChatFollowAll(Integer userNO);
}
