package com.ecobank.app.chat.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecobank.app.chat.service.ChatService;

@Controller
public class ChatSearchController {
	
	private ChatService chatService;
	
	@Autowired
	ChatSearchController(ChatService chatService){
		this.chatService = chatService;
	}
	
	//
	@GetMapping("/chatSearch")
	public String ChatRooms(Model model){
		return "chat/openChatSearch";
	}
}
