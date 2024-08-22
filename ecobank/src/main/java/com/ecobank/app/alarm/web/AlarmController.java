package com.ecobank.app.alarm.web;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.ecobank.app.alarm.service.AlarmService;
import com.ecobank.app.alarm.service.AlarmVO;
@Controller
public class AlarmController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private AlarmService alarmService;

    
    @Autowired
	public AlarmController(AlarmService alarmService) {
		this.alarmService = alarmService;
	}
    
    @MessageMapping("/alarm.send/{refNo}")
    public void sendAlarm(@Payload AlarmVO alarm, @DestinationVariable String refNo, Principal principal) {
        // 알림 저장
        //AlarmVO savedAlarm = alarmService.saveAlarm(alarm);
    	String userId=null;
    	System.out.println(principal);
    	System.out.println(alarm);
    	System.out.println(refNo);
    	
    	String alarmCode = alarm.getAlarmCode();
    	
    	if(alarmCode.equals("H2")) {
    		System.out.println(Integer.parseInt(refNo));
    		userId = alarmService.getUserIdFromConfirmNo(Integer.parseInt(refNo));
    		System.out.println(userId);
    	}
    	
    	if (userId != null) {
            messagingTemplate.convertAndSendToUser(userId, "/queue/alarms", alarm);
            System.out.println("Sent alarm to user: " + userId);
        } else {
            System.err.println("User ID is null. Cannot send alarm.");
        }
    	
    }
}