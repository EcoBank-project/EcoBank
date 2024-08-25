package com.ecobank.app.chat.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecobank.app.chat.service.ChatCheckDTO;
import com.ecobank.app.chat.service.ChatRoomVO;
import com.ecobank.app.chat.service.ChatSearchService;

@Controller
public class ChatSearchController {

	private ChatSearchService chatSearchService;

	@Autowired
	ChatSearchController(ChatSearchService chatSearchService) {
		this.chatSearchService = chatSearchService;
	}

	// 오픈 채팅방 홈페이지
	@GetMapping("/chatSearch")
	public String ChatRooms(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String search,
			Model model) {
		int totalPages = chatSearchService.getTotalPages(search);
		List<ChatRoomVO> list = chatSearchService.OpenChatRoomList(page, search);
		model.addAttribute("openChatList", list);
		model.addAttribute("search", search);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		return "chat/openChatSearch";
	}

	// 방에 있는 체크
	@PostMapping("/checkParticipation")
	@ResponseBody
	public ChatCheckDTO chatRoomPartCheck(@RequestParam Integer chatRoomId, HttpSession httpSession) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		ChatCheckDTO chatCheck = chatSearchService.openChatCheck(chatRoomId, userNo);
		return chatCheck;
	}

	// 비밀번호 체크
	@PostMapping("/verifyPassword")
	@ResponseBody
	public boolean chatRoomPasswordCheck(@RequestParam Integer chatRoomId, @RequestParam String password, HttpSession httpSession) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		return chatSearchService.ChatPasswordComparison(chatRoomId, userNo, password);
	}
	
	// 채팅방 검색에서 들어감
	@PostMapping("/searchOpenChatEnter")
	@ResponseBody
	public void chatRoomSearchEnter(@RequestParam Integer chatRoomId, HttpSession httpSession) {
		Integer userNo = (Integer) httpSession.getAttribute("userNo");
		chatSearchService.ChatSearchOpenEnter(chatRoomId, userNo);
	}
}
