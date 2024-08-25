package com.ecobank.app.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.chat.service.ChatRoomVO;

public interface ChatSearchMapper {
	//오픈 채팅 전체 조회
	public List<ChatRoomVO> selectOpenChatAll (@Param("search") String search, @Param("startRow") int startRow, @Param("endRow") int endRow);
	//오픈 채팅 조건에 따른 갯수
	public int selectOpenChatCnt(String search);
	//채팅방에 있는지 체크
	public int selectOpenUserCheck(@Param("chatNo") Integer chatNo, @Param("userNo") Integer userNo);
	//채팅방 비밀번호 체크
	public String selectOpenChatPasswordCheck(Integer chatNo);
	//채팅방 비밀번호 확인
	public String selectOpenChatPassword(@Param("chatNo") Integer chatNo, @Param("userNo") Integer userNo);
}
