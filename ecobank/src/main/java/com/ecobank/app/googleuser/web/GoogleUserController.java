package com.ecobank.app.googleuser.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ecobank.app.googleuser.service.GoogleUserInfoForm;
import com.ecobank.app.googleuser.service.GoogleUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class GoogleUserController {

	private final GoogleUserService userService;
    private final HttpSession session;

	@GetMapping("/googleSignup")
    public ModelAndView googleSignupForm() {
        ModelAndView modelAndView = new ModelAndView("users/googleUserInfo");
       
        String useId = (String) session.getAttribute("useId");
        GoogleUserInfoForm form = new GoogleUserInfoForm();
        form.setUseId(useId);
        
        modelAndView.addObject("googleUserInfoForm", form);
        return modelAndView;
    }
	
	@PostMapping("/googleSignup")
	public String googleSignUp(@ModelAttribute GoogleUserInfoForm googleUserInfoForm,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "uesrs/googleUserInfo";
		}
		userService.googleUpdate(googleUserInfoForm);
		return "redirect:/";
		
	}
	
	
}
