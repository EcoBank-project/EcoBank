package com.ecobank.app.users.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecobank.app.users.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository; // JPA를 통한 기본 CRUD
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper; // MyBatis를 통한 추가적인 쿼리

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
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public String checkID(String uid) {
        return userRepository.findByUseId(uid).getUseId();
    }

    public String getUserIdByPhoneNumber(String phoneNumber) {
        return userMapper.findUserIdByPhoneNumber(phoneNumber);
    }

    public void updatePassword(String email, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        userMapper.updatePassword(email, encodedPassword); // MyBatis를 통한 비밀번호 업데이트
    }
    
    public Users findByUseId(String useId) {
        return userRepository.findByUseId(useId); // JPA를 통한 사용자 조회
    }
}