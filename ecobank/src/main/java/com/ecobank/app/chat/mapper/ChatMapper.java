package com.ecobank.app.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.chat.service.ChatFollowVO;
import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatPartVO;
import com.ecobank.app.chat.service.ChatRoomVO;

// 서버 기준
public interface ChatMapper {
	// 로그인한 회원 채팅방 전체 조회
	public List<ChatRoomVO> selectChatRoomAll(Integer userNo);
	// 로그인한 회원 채팅방 정보 조회
	public ChatRoomVO selectChatRoomInfo(@Param("chatNo") Integer chatNo, @Param("userNo") Integer userNo);
	// 채팅방 채팅로그 조회
	public List<ChatMessageVO> selectChatMessage(Integer chatNo);
	// 채팅방 채팅로그 기록
	public int insertChatMessage(ChatMessageVO chatMessageVO);
	
	// 채팅방 만들기
	public int insertChatRoom(ChatRoomVO chatRoomVO);
	// 채팅방 참여자
	public int insertChatUser(@Param("chatNo") Integer chatNo, @Param("userNo") Integer userNo);
	// 채팅방 타입
	public String getChatRoomType(Integer chatNo);
	// 채팅방 참여자 아이디 조회
	public List<String> selectAllChatUser(Integer chatNo);
	
	// 채팅방 참가자 수
	public int selectUsersChatRoom(Integer chatNo);
	// 채팅방 나가기
	public int updateChatEntry(@Param("userNo")Integer userNo, @Param("chatNo")Integer chatNo);
	// 채팅방 삭제
	public int deleteChatRoom(Integer chatNo);
	// 채팅방 참여자 삭제
	public int deleteChatPart(Integer chatNo);
	// 채팅방 메시지 삭제
	public int deleteAllMessage(Integer chatNo);
	// 채팅방 나가고 남은 사람 조회
	public List<String> selectLeaveUser(@Param("userNo")Integer userNo, @Param("chatNo")Integer chatNo);
	// 채팅방 방장 조회
	public int selectRoomManager(@Param("userNo")Integer userNo, @Param("chatNo")Integer chatNo);
	// 채팅방 이름 변경
	public int updateChatChangeName(@Param("chatName")String chatName, @Param("chatNo")Integer chatNo);
	// 채팅방에 없는 팔로우 목록
	public List<ChatFollowVO> selectChatFollowInfo(@Param("followerId") Integer userNo, @Param("chatNo")Integer chatNo);
	// 채팅방 닉네임 단일 조회
	public String selectChatUserName(Integer userNo);
	// 오픈 채팅방 업데이트
	public int updateOpenChatChange(ChatRoomVO chatRoom);
	// 채팅방 닉네임 조회
	public List<ChatPartVO> selectChatRoomUsers(Integer chatNo);
	// 팔로우 목록
	public List<ChatFollowVO> selectChatFollowAll(Integer userNO);
	
	// 언어 조회
	public String selectLaguageCode(String userId);
	// 언어 변경
	public int updateLaguageCode(@Param("laguageCode") String lagCode, @Param("userNo") Integer userNo);
}
