package com.ecobank.app.mail.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecobank.app.mail.service.MailService;

@Controller
public class MailController {
	
	@Autowired
	private MailService mailService;

	@ResponseBody
	@PostMapping("/user/mail")
	public String MailSend(String mail) {
		
		int number = mailService.sendMail(mail);

		String num = "" + number;

		return num;
	}
}
