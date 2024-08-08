package com.ecobank.app.chat.web;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecobank.app.chat.service.ChatMessageVO;
import com.ecobank.app.chat.service.ChatRoomVO;

@Controller
public class ChatController {
	
	@Autowired
	private HttpSession httpSession;
	
	// 채팅 화면
	@GetMapping("/chatRoom")
	public String chat(Model model) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		String nickname = (String) httpSession.getAttribute("nickname");
		model.addAttribute("userNo", userNo);
		model.addAttribute("nickname", nickname);
		return "chat/chatRoom";
	}
	// 채팅방 조회
	@GetMapping("")
	public List<ChatRoomVO> loadChat(){
		
		return null;
	}
	
	// 채팅방 입장
	@MessageMapping("/chat.enter")
	@SendTo("/topic/message")
	public ChatMessageVO enterUser(@Payload ChatMessageVO message) {
		return message;
	}
	
	
	// 채팅방 대화
	@MessageMapping("/chat.message")
	@SendTo("/topic/messages")
	public ChatMessageVO sendUser(@Payload ChatMessageVO message) {
		return message;
	}
	
	// 채팅방 퇴장
	@MessageMapping("/chat.exit")
	@SendTo("/topic/messages")
	public ChatMessageVO exitUser(@Payload ChatMessageVO message) {
		return message;
	}
}
