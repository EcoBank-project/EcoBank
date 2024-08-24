package com.ecobank.app.mypage.web;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecobank.app.admin.service.UserVO;
import com.ecobank.app.mypage.service.MypageChallVO;
import com.ecobank.app.mypage.service.MypageFollowService;
import com.ecobank.app.mypage.service.MypageService;
import com.ecobank.app.users.service.UserRepository;
import com.ecobank.app.users.service.UserService;
import com.ecobank.app.users.service.UserStatistics;
import com.ecobank.app.users.service.Users;

@Controller
public class MypageController {

	@Autowired
	private UserService userService;

	@Autowired
	private MypageService mypageService;

	@Autowired
	private MypageFollowService followService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("mypage")
	public String getMypage(HttpSession session, Model model) {
		Integer userNo = (Integer) session.getAttribute("userNo");

		UserStatistics userStatistics = userService.getUserStatistics(userNo);

		model.addAttribute("totalScore", userStatistics.getTotalScore());
		model.addAttribute("followerCount", userStatistics.getFollowerCount());
		model.addAttribute("followingCount", userStatistics.getFollowingCount());
		model.addAttribute("userProfile", userStatistics.getUserProfile());
		
		return "mypage/mypage"; // mypage.html 템플릿을 렌더링 }
	}

	@GetMapping("/user/info")
	@ResponseBody
	public UserStatistics getUserInfo(HttpSession session) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		UserStatistics userStatistics = userService.getUserStatistics(userNo);
		return userStatistics;
	}

	@GetMapping("/userEdit")
	public String getUserEdit() {
		return "mypage/userEdit";
	}

	@GetMapping("/userDel")
	public String getUserDel() {
		return "mypage/userDel";
	}
		

	

	// 비밀번호 재확인(수정)
	@PostMapping("/validate-password")
	public String validatePassword(@RequestParam("password") String password, Model model) {
		// 현재 로그인한 사용자 정보 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Optional<Users> user = userService.findByUserId(username);

		System.out.println("user.get().getPassword() : " + user.get().getPassword());

		// 비밀번호 확인
		if (passwordEncoder.matches(password, user.get().getPassword())) {
			// 비밀번호가 맞는 경우
			return "mypage/update-info";
		} else {
			// 비밀번호가 틀린 경우
			return "mypage/mypage";
		}
	}

	// 비밀번호 재확인(삭제)
	@PostMapping("/validate-password2")
	public String validatePassword2(@RequestParam("password") String password, Model model) {
		// 현재 로그인한 사용자 정보 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Optional<Users> user = userService.findByUserId(username);

		System.out.println("user.get().getPassword() : " + user.get().getPassword());

		// 비밀번호 확인
		if (passwordEncoder.matches(password, user.get().getPassword())) {
			// 비밀번호가 맞는 경우
			return "mypage/delete-user";
		} else {
			// 비밀번호가 틀린 경우
			return "mypage/mypage";
		}
	}

	@PostMapping("/update-nickname")
	public String updateNickname(@RequestParam("newNickname") String newNickname, HttpSession session) {
		// 입력한 닉네임 값이 중복된 값인지 체크
		String useId = (String) session.getAttribute("useId");
		Users user = userService.findByUseId(useId);

		if (user != null) {
			user.setNickName(newNickname);
			userRepository.save(user);
			session.setAttribute("nickname", newNickname);
		}

		return "redirect:/mypage";
	}

	@PostMapping("/update-email")
	public String updateEmail(@RequestParam("newEmail") String newEmail, HttpSession session) {
		String useId = (String) session.getAttribute("useId");
		Users user = userService.findByUseId(useId);

		if (user != null) {
			user.setUseId(newEmail);
			userRepository.save(user);
			session.setAttribute("useId", newEmail);
		}

		return "redirect:/mypage";
	}

	@PostMapping("/update-password")
	public String updatePassword(@RequestParam("newPassword") String newPassword,
			@RequestParam("chkPassword") String chkPassword, HttpSession session) {
		String useId = (String) session.getAttribute("useId");
		Users user = userService.findByUseId(useId);

		System.out.println("newpassword : " + newPassword);
		System.out.println("chkpassword : " + chkPassword);

		if (user != null && newPassword.equals(chkPassword)) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
			session.setAttribute("userPw", passwordEncoder.encode(newPassword));

		}

		return "redirect:/mypage";
	}

	@PostMapping("/api/withdrawal")
	@ResponseBody
	public Map<String, Object> processWithdrawal(@RequestParam("userNo") Integer userNo) {
		Map<String, Object> response = new HashMap<>();
		System.out.println("`````````````userNo : " + userNo);
		boolean success = userService.withdrawUser(userNo);
		response.put("success", success);
		return response;
	}

	@GetMapping("/api/withdrawal-info")
	@ResponseBody
	public List<MypageChallVO> getWithdrawalInfo(@RequestParam("userNo") Integer userNo) {
		List<MypageChallVO> challInfo = mypageService.getChallengeInfo(userNo);
		return challInfo;
	}
	
	
	
	@GetMapping("/ongoingChall")
	public String getChallenge(HttpSession session, Model model) {
		Integer userNo = (Integer) session.getAttribute("userNo");

		List<MypageChallVO> challengeInfo = mypageService.getChallengeInfo(userNo);
		model.addAttribute("challengeInfo", challengeInfo);
		return "mypage/ongoingChall";
	}
	
	@GetMapping("/exitChall")
	public String getExitChallenge(HttpSession session, Model model) {
		Integer userNo = (Integer) session.getAttribute("userNo");

		List<MypageChallVO> challengeInfo = mypageService.getExitChallengeInfo(userNo);
		model.addAttribute("challengeInfo", challengeInfo);
		return "mypage/exitChall";
	}
	

	@PostMapping("/cancelChallenge")
	@ResponseBody
	public String cancelChallenge(@RequestParam("challNo") int challNo, @RequestParam("userNo") int userNo) {
		try {
			boolean success = mypageService.cancelChallenge(userNo, challNo);
			if (success) {
				return "success";
			} else {
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@GetMapping("/user/following")
	@ResponseBody
    public List<UserVO> getFollowingList(@RequestParam("userNo") int userNo) {
        return followService.getFollowingList(userNo);
    }

    // 팔로워 목록
    @GetMapping("/user/follower")
    @ResponseBody
    public List<UserVO> getFollowerList(@RequestParam("userNo") int userNo) {
        return followService.getFollowerList(userNo);
    }

    // 프로필 이미지 수정
    @PostMapping("/user/changeProfileImage")
    public ResponseEntity<?> changeProfileImage(@RequestParam("profileImage") MultipartFile profileImage, HttpSession session) {
        Integer userNo = (Integer) session.getAttribute("userNo");

        try {
            String fileName = profileImage.getOriginalFilename();
            String filePath = "D:/upload/profile/" + fileName; // 이미지 저장 경로
            System.out.println("-------" + profileImage.getOriginalFilename());
            System.out.println("-------" + fileName);

            // 파일 저장
            File file = new File(filePath);
            profileImage.transferTo(file);

            // 사용자 정보 업데이트
            mypageService.updateProfileImage(userNo, fileName);

            // 응답 반환
            return ResponseEntity.ok(Collections.singletonMap("newImageUrl", "/images/" + fileName));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 저장 실패");
        }
    }
}

    
    
