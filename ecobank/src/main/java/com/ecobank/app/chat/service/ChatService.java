package com.ecobank.app.chat.service;

import java.util.List;


import org.springframework.web.multipart.MultipartFile;

// 사용자 기준
public interface ChatService {
	//로그인한 회원 전체 채팅방 조회
	public List<ChatRoomVO> chatRoomList(Integer chatId, String nickName);
	//로그인한 회원 채팅방 정보 조회
	public ChatRoomVO chatRoomInfo(Integer chatNo, Integer userNo, String nickName);
	//채팅방 채팅로그 기록 조회
	public List<ChatMessageVO> chatMessageList(Integer chatNo);
	//채팅방 채팅로그 기록 저장
	public ChatMessageVO ChatMessageInsert(ChatMessageVO chatMessageVO);
	
	//채팅방 만들기
	public int ChatRoomInsert(ChatRoomDTO chatRoom, Integer userNo);
	//오픈 채팅방 만들기
	public int ChatOpenInsert(ChatRoomVO chatRoom, Integer userNo,  MultipartFile[] images);
	//채팅방 이름 조회
	public Integer getChatName(String chatName);
	//채팅방 초대된 사람 나가고 다시 업데이트
	public int chatStateUpdate(Integer userNo, Integer chatNo);
	
	//채팅방 타입 조회
	public String chatRoomType(Integer chatNo);
	//채팅방 참여자
	public int ChatUserInsert(Integer chatNo, Integer userNo);
	//채팅방 참여자 아이디
	public List<String> ChatUserList(Integer chatNo);
	
	// 채팅방 참가자 수
	public int getUsersChatRoom(Integer chatNo, ChatMessageVO message, String nickName);
	// 채팅방 나가기
	public int chatEntryUpdate(Integer userNo, Integer chatNo);
	
	// 채팅방 메시지 삭제
	public int chatAllMessageDelete(Integer chatNo);
	
	// 채팅방 남은 아이디 조회
	public List<String> chatLeaveUser(Integer userNo, Integer chatNo);
	// 채팅방 방장 조회
	public int getRoomManager(Integer userNo, Integer chatNo);
	// 채팅방 이름 변경
	public int chatNameChangeUpdate(String chatName, Integer chatNo);
	// 채팅방 참여자 닉네임
	public List<ChatPartVO> ChatRoomUsersList(Integer chatNo);
	// 채팅방 참여자 닉네임 단건
	public String chatNickName(Integer userNo);
	// 채팅 번역
	public String translateMessage(String text, String targetLanguage);
	// 언어 조회
	public String laguageCodeSelect(String userId);
	// 언어 변경
	public int laguageCodeUpdate(String lagCode, Integer userNo);
	//팔로우 목록
	public List<ChatFollowVO> chatFollowList(Integer userNo);
	//채팅방에 없는 팔로우 목록
	public List<ChatFollowVO> chatFollowListInfo(Integer userNo, Integer chatNo);
	//오픈채팅방 업데이트
	public int OpenChatChangeUpdate(ChatRoomVO chatRoom, MultipartFile[] images);
	
	//회원 프로필
	public ChatRoomUserVO ChatProfileInfo(Integer userNo);
	
	//파일 업로드
	public ChatFileVO getfileURL(MultipartFile[] images);
}
