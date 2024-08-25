package com.ecobank.app.chat.service;

import java.util.List;

public interface ChatSearchService {
	//오픈 채팅방 조회
	public List<ChatRoomVO> OpenChatRoomList (int page, String search);
	//오픈 채팅방 총 갯수
	public int getTotalPages(String search);
	//오픈 채팅 체크
	public ChatCheckDTO openChatCheck(Integer chatNo, Integer userNo);
	//비밀 번호 비교
	public Boolean ChatPasswordComparison(Integer chatNo, Integer userNo, String passWord);
	//오픈 채팅방 비밀번호 없이 검색해서 들어감
	public void ChatSearchOpenEnter(Integer chatNo, Integer userNo);
}
