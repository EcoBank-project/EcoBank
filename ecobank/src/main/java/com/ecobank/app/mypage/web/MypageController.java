package com.ecobank.app.mypage.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecobank.app.users.service.UserService;
import com.ecobank.app.users.service.UserStatistics;
import com.ecobank.app.users.service.Users;

@Controller
public class MypageController {

	@Autowired
	private UserService userService;

	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("mypage")
	public String getMypage(HttpSession session, Model model) {
		Integer userNo = (Integer) session.getAttribute("userNo");

		UserStatistics userStatistics = userService.getUserStatistics(userNo);

		model.addAttribute("totalScore", userStatistics.getTotalScore());
		model.addAttribute("followerCount", userStatistics.getFollowerCount());
		model.addAttribute("followingCount", userStatistics.getFollowingCount());

		return "mypage/mypage"; // mypage.html 템플릿을 렌더링 }

	}
	
	@GetMapping("/userEdit")
	public String getUserEdit() {
		return "mypage/userEdit";
	}
	
	
}