package com.ecobank.app.chat.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
	
	@GetMapping("/chat")
	public String chat(Model model) {
		return "chat";
	}
	
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public String send(String message) {
		return message;
	}
}
