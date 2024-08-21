package com.ecobank.app.alarm.web;
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

    @MessageMapping("/alarm.send/{userId}")
    public void sendAlarm(@Payload AlarmVO alarm, @DestinationVariable String userId) {
        // 알림 저장
        AlarmVO savedAlarm = alarmService.saveAlarm(alarm);

        // 특정 사용자에게 알림 전송
        messagingTemplate.convertAndSendToUser(userId, "/queue/alarms", savedAlarm);
    }
}