package com.ecobank.app.chat.web;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecobank.app.chat.service.ChatMessageVO;

@Controller
public class ChatController {
	
	@Autowired
	private HttpSession httpSession;
	
	// 채팅 화면
	@GetMapping("/chatRoom")
	public String chat(Model model) {
		String userId = (String) httpSession.getAttribute("useId");
		System.out.println(userId);
		return "chat/chatRoom";
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
