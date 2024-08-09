package com.ecobank.app.sms.service;

import java.security.SecureRandom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
public class SmsUtil {

	@Value("${coolsms.api.key}")
	private String key;
	
	@Value("${coolsms.api.secret}")
	private String secret;
	
	@Value("${coolsms.sms.phoneNumber}")
	private String phoneNum;
	
    private DefaultMessageService messageService;
    private SecureRandom random = new SecureRandom();

    @PostConstruct
    private void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(key, secret, "https://api.coolsms.co.kr");
    }

    // 랜덤 인증번호 생성
    public String generateRandomCode(int length) {
        int bound = (int) Math.pow(10, length);
        int number = random.nextInt(bound);
        return String.format("%0" + length + "d", number);
    }

    // 단일 메시지 발송
    public SingleMessageSentResponse sendOne(String to, String verificationCode) {
        Message message = new Message();
        message.setFrom(phoneNum);
        message.setTo(to);
        message.setText("[ECOBANK] 아래의 인증번호를 입력해주세요\n" + verificationCode);

        return this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }
}