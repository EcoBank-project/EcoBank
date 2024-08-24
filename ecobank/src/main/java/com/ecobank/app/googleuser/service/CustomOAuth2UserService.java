package com.ecobank.app.googleuser.service;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ecobank.app.users.service.UserRepository;
import com.ecobank.app.users.service.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId,
        		userNameAttributeName,
        		oAuth2User.getAttributes());

        // 사용자 정보를 가져오거나 새로 저장
        
        Users user = saveOrUpdateUser(attributes);

        // 세션에 사용자 정보 저장
		
        session.setAttribute("useId", user.getUseId());
		session.setAttribute("nickname", user.getNickName());
		session.setAttribute("userState", user.getUserState());
		session.setAttribute("userNo", user.getUserNo());
		session.setAttribute("resp", "A1");
       
		String userRole = "A1";
        
        // 권한을 포함하여 OAuth2User 객체 생성 후 반환
        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(userRole)),  // 적절한 권한 설정
            attributes.getAttributes(),
            "email"
//            attributes.getAttributes(),
//            attributes.getNameAttributeKey()
        );
    }

	private Users saveOrUpdateUser(OAuthAttributes attributes) {
		Users user = userRepository.findByUseId(attributes.getUseId()).orElse(
				 Users.builder()
	                .useId(attributes.getUseId())
	                //.nickName("Default Nickname") // 기본값 설정
	                //.tell("Default Tell") // 기본값 설정
	                .resp("A1") // 기본 권한 설정
	                .build());
		return userRepository.save(user);
	}


}