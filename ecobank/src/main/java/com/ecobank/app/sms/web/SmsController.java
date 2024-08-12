package com.ecobank.app.sms.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecobank.app.sms.service.SmsUtil;
import com.ecobank.app.users.service.UserService;

@Controller
public class SmsController {

    @Autowired
    private SmsUtil smsUtil;
    
    @Autowired
    private UserService userService;
    
    
    private Map<String, String> verificationCodes = new HashMap<>(); // 전화번호와 인증번호를 저장할 맵

    @PostMapping("/user/send")
    public ResponseEntity<Map<String, String>> sendSms(@RequestParam String phoneNumber) {
        String verificationCode = smsUtil.generateRandomCode(6); // 6자리 인증번호 생성
        smsUtil.sendOne(phoneNumber, verificationCode); // 인증번호와 함께 전송
        verificationCodes.put(phoneNumber, verificationCode);
        Map<String, String> response = new HashMap<>();
        response.put("message", "인증번호 전송!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/verify")
    public ResponseEntity<Map<String, String>> verifySms(
    		@RequestParam String phoneNumber,
    		@RequestParam String verificationCode
    		) {
    	String storedCode = verificationCodes.get(phoneNumber);
        Map<String, String> response = new HashMap<>();
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Provided Code: " + verificationCode);
        System.out.println("Stored Code: " + storedCode);        
        if (storedCode != null && storedCode.equals(verificationCode)) {
            verificationCodes.remove(phoneNumber); // 인증번호 검증 후 삭제
            String userId = userService.getUserIdByPhoneNumber(phoneNumber); // 회원 ID 조회
            response.put("message", "인증 성공");
            response.put("userId", userId);
        } else {
            response.put("message", "인증 실패");
        }
        return ResponseEntity.ok(response);

    }
}