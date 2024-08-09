package com.ecobank.app.chat.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatRoomVO;
import com.ecobank.app.chat.service.ChatService;

@Controller
public class ChatRoomController {
	private ChatService chatService;
	
	@Autowired
	ChatRoomController(ChatService chatService){
		this.chatService = chatService;
	}
	
	@Autowired
	private HttpSession httpSession;
	
	// 채팅방 목록&채팅방 참여
	@GetMapping("/chatRoom")
	public String ChatRooms(Model model){
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		String nickname = (String) httpSession.getAttribute("nickname");
		
		List<ChatRoomVO> chatList = chatService.chatRoomList(userNo);
		model.addAttribute("userNo", userNo);
		model.addAttribute("nickname", nickname);
		model.addAttribute("chatRooms", chatList);
		return "chat/chatRoom";
	}
	
	// 특정 채팅방 채팅로그 조회
	@GetMapping("/chatRoom/{roomId}")
	@ResponseBody
	public List<ChatMessageVO> ChatRoom(@PathVariable Integer roomId) {
		List<ChatMessageVO> msgList = chatService.chatMessageList(roomId);
		System.out.println("확인");
		return msgList;
	}
	
	// 채팅방 생성
}
