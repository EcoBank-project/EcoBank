package com.ecobank.app.users.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecobank.app.users.service.UserCreateForm;
import com.ecobank.app.users.service.UserRepository;
import com.ecobank.app.users.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@CrossOrigin(origins = "http://localhost:8080") // 클라이언트 URL을 설정합니다.
public class UsersController {

	private final UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("login")
	public String goLogin() {
		return "users/login";
	}

	/*
	 * @PostMapping("login") public ModelAndView login(@RequestParam String
	 * useId, @RequestParam String password) { boolean isAuthenticated =
	 * userService.authenticate(useId, password); if (isAuthenticated) { return new
	 * ModelAndView("redirect:/"); } else { ModelAndView mav = new
	 * ModelAndView("users/login"); mav.addObject("error",
	 * "로그인 실패: 사용자 이름 또는 비밀번호가 올바르지 않습니다."); return mav; } }
	 */

	@GetMapping("signup")
	public String signUp(UserCreateForm userCreateForm) {
		return "users/signup_form";
	}

	@PostMapping("signup")
	public String signUp(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "users/signup_form";
		}

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
			return "users/signup_form";
		}

		userService.create(userCreateForm.getNickName(), userCreateForm.getUseId(), userCreateForm.getPassword1(),
				userCreateForm.getTell());

		return "redirect:/";
	}
	
    @GetMapping("/user/api/check-duplicate")
    @ResponseBody
    public Map<String, Boolean> checkDuplicate(@RequestParam String useId) { 
        Map<String, Boolean> response = new HashMap<>();
        boolean exists = userRepository.findByUseId(useId) != null; // db에 입력받은 매개값 useId가 있는 지 확인
        response.put("exists", exists); // 있으면 <exists, true> 없으면 <exists, false>
        return response;
    }
    
	@GetMapping("findid")
	public String goFindId() {
		return "users/findid";
	}
	
	@GetMapping("findpw")
	public String goFindPw() {
		return "users/findpw";
	}

}
