package com.ecobank.app.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecobank.app.users.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserMapper userMapper;
	
	public Users create(String nickname, String useId, String password, String tell) {
		Users user = new Users();
		user.setUseId(useId);
		user.setPassword(passwordEncoder.encode(password));
		user.setNickName(nickname);
		user.setTell(tell);
		this.userRepository.save(user);

		return user;
	}

	public boolean authenticate(String useId, String password) {
		Users user = userRepository.findByUseId(useId);
		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			return true;
		}
		return false;
	}
	
	public String checkID(String uid) { 
    	  return userRepository.findByUseId(uid).getUseId();
    };
    
    public String getUserIdByPhoneNumber(String phoneNumber) {
        return userMapper.findUserIdByPhoneNumber(phoneNumber);
    }
    
}
