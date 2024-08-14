package com.ecobank.app.googleuser.service;

import org.springframework.stereotype.Service;

import com.ecobank.app.users.service.UserRepository;
import com.ecobank.app.users.service.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GoogleUserService {
	
	private final UserRepository userRepository;
	
	
    public Users googleUpdate(GoogleUserInfoForm googleUserInfoForm) {
        Users existingUser = userRepository.findByUseId(googleUserInfoForm.getUseId()).orElse(null);
    	
    	if (existingUser != null) {
            // 기존 사용자 정보 업데이트
            existingUser.setNickName(googleUserInfoForm.getNickName());
            existingUser.setTell(googleUserInfoForm.getTell());
            System.out.println("Updating user: " + existingUser);
            return this.userRepository.save(existingUser); // 기존 사용자 업데이트
        } else {
            // 새 사용자 생성 및 저장
            Users newUser = new Users();
            newUser.setUseId(googleUserInfoForm.getUseId());
            newUser.setNickName(googleUserInfoForm.getNickName());
            newUser.setTell(googleUserInfoForm.getTell());
            System.out.println("Saving new user: " + newUser);
            return this.userRepository.save(newUser); // 새 사용자 삽입
        }
    }	
}
