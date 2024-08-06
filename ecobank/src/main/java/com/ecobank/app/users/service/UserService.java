package com.ecobank.app.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public Users create(String nickname, String useId, String password) {
		Users user = new Users();
		user.setNickName(nickname);
		user.setUseId(useId);
		user.setPassword(passwordEncoder.encode(password));
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
}
