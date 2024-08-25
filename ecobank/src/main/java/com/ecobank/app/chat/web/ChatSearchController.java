package com.ecobank.app.chat.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecobank.app.chat.service.ChatRoomVO;
import com.ecobank.app.chat.service.ChatSearchService;

@Controller
public class ChatSearchController {
	
	private ChatSearchService chatSearchService;
	
	@Autowired
	ChatSearchController(ChatSearchService chatSearchService){
		this.chatSearchService = chatSearchService;
	}
	//오픈 채팅방 홈페이지
	@GetMapping("/chatSearch")
	public String ChatRooms(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String search, Model model){
		int totalPages = chatSearchService.getTotalPages(search);
		List<ChatRoomVO> list = chatSearchService.OpenChatRoomList(page, search);	
		model.addAttribute("openChatList", list);
		model.addAttribute("search", search);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
		return "chat/openChatSearch";
	}
}
