package com.ecobank.app.users.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ecobank.app.users.service.UserCreateForm;
import com.ecobank.app.users.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UsersController {

	private final UserService userService;

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

		userService.create(userCreateForm.getNickName(), userCreateForm.getUseId(), userCreateForm.getPassword1());

		return "redirect:/";
	}

}
