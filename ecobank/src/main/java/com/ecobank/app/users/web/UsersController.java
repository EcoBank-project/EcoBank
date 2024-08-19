package com.ecobank.app.users.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecobank.app.users.service.UserCreateForm;
import com.ecobank.app.users.service.UserRepository;
import com.ecobank.app.users.service.UserService;
import com.ecobank.app.users.service.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UsersController {

	private final UserService userService;

	@Autowired
	private UserRepository userRepository;


	
	@GetMapping("login")
	public String goLogin() {
		return "users/login";
	}

	@GetMapping("signup")
	public String signUp(UserCreateForm userCreateForm) {
		return "users/signup_form";
	}

	@PostMapping("signup")
	public String signUp(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) { // 
			return "users/signup_form";
		}

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
			return "users/signup_form";
		}

		userService.create(userCreateForm);

		return "redirect:/";
	}
	
    @GetMapping("/user/api/check-duplicate")
    @ResponseBody
    public Map<String, Boolean> checkDuplicate(@RequestParam String useId) { 
        Map<String, Boolean> response = new HashMap<>();
        boolean exists = !userRepository.findByUseId(useId).isEmpty(); // db에 입력받은 매개값 useId가 있는 지 확인
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
	
	@GetMapping("reset_pw")
	public String showResetPasswordPage(@RequestParam("email") String mail, Model model) {
	    model.addAttribute("mail", mail);
	    return "users/reset_pw";
	}
	
	@PostMapping("reset_pw")
	public String resetPassword(@RequestParam("email") String email, @RequestParam("newPassword") String newPassword) {
	    // 비밀번호 업데이트 로직 구현
	    userService.updatePassword(email, newPassword);
	    return "redirect:/login";
	}
	

	
} 
