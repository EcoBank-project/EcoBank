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

    
    public Users create(UserCreateForm userCreateForm) {
        Users user = new Users(); // user 인스턴스 만들어서
        user.setUseId(userCreateForm.getUseId());
        user.setPassword(passwordEncoder.encode(userCreateForm.getPassword1()));
        user.setNickName(userCreateForm.getNickName());
        user.setTell(userCreateForm.getTell()); // 값을 넣어주고
        this.userRepository.save(user); // db에 insert 됨
        return user;
    }

    public boolean authenticate(String useId, String password) {
        Users user = userRepository.findByUseId(useId);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public String checkID(String uid) {
        return userRepository.findByUseId(uid).getUseId(); // JPA를 톹ㅇ한 
    }

    public String getUserIdByPhoneNumber(String phoneNumber) {
        return userMapper.findUserIdByPhoneNumber(phoneNumber); // MyBatis를 통한 사용자 조회
    }

    public void updatePassword(String useId, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        userMapper.updatePassword(useId, encodedPassword); // MyBatis를 통한 비밀번호 업데이트
    }
    
    public Users findByUseId(String useId) {
        return userRepository.findByUseId(useId); // JPA를 통한 사용자 조회
    }
}