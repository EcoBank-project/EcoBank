package com.ecobank.app.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ecobank.app.users.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    
	private final UserMapper userMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

			OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
	        
			boolean userExists = checkUserInDatabase(oauth2User);
			
			if(userExists) {
				response.sendRedirect("/");
			}
			else {
				response.sendRedirect("/googleSignup");
			}
		
	}

	private boolean checkUserInDatabase(OAuth2User oauth2User) {
        String useId = (String) oauth2User.getAttributes().get("email");
        
        // 사용자 정보를 조회하여 null 여부 확인
        String nickname = userMapper.findUserInfoByUseId(useId);
        return nickname != null; // 닉네임 있으면 true 없으면 false
	}

}
