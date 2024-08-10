package com.ecobank.app.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ecobank.app.security.service.LoginUserVO;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String redirectUrl = "/login";

		HttpSession session = request.getSession(true);

		LoginUserVO userDetail = (LoginUserVO) authentication.getPrincipal();

		session.setAttribute("useId", userDetail.getUsername());
		session.setAttribute("nickname", userDetail.getNickname());
		session.setAttribute("userState", userDetail.getUserState());
		session.setAttribute("userNo", userDetail.getUserNO());
		session.setAttribute("resp", userDetail.getResp());
		
		System.out.println("세션에 저장되는 정보들");
		System.out.println("아이디 : " + userDetail.getUsername());
		System.out.println("닉네임 : " + userDetail.getNickname());
		System.out.println("유저상태 : " + userDetail.getUserState());
		System.out.println("유저번호 :" + userDetail.getUserNO());
		
		// 일반회원
		if(userDetail.getResp().equals("A1")) {
			redirectUrl = "/";
		// 관리자
		}else {
			redirectUrl = "/admin";
		}
		
		response.sendRedirect(redirectUrl);
	}

}
