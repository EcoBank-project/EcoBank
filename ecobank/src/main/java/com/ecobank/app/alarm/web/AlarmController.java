package com.ecobank.app.alarm.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecobank.app.alarm.service.AlarmService;
import com.ecobank.app.alarm.service.AlarmVO;
import com.ecobank.app.home.service.ChallengeVO;
import com.ecobank.app.home.service.RankingVO;

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

		String userId = null;
		System.out.println(principal);
		System.out.println(alarm);
		System.out.println(refNo);

		String alarmCode = alarm.getAlarmCode();

		System.out.println(Integer.parseInt(refNo));
		// 알람코드 확인(H2:챌린지 인증좋아요, H3:피드 좋아요)
		// 해당 참조번호로부터 작성자(수신자)아이디 확인
		if (alarmCode.equals("H2")) {
			userId = alarmService.getUserIdFromConfirmNo(Integer.parseInt(refNo));
		} else if (alarmCode.equals("H3")) {
			userId = alarmService.getUserIdFromfeedNo(Integer.parseInt(refNo));
		}
		else if(alarmCode.equals("H5")) {
			userId = alarmService.getUserIdFromFollowingNo(Integer.parseInt(refNo));
		}
		System.out.println(userId);

		// 수신자가 있어야만 메세지 보낼수 있도록 조건
		if (userId != null) {
			AlarmVO alarmData = alarmService.checkAlarmExists(alarm);
			try {
				System.out.println("existAlarm"+alarmData.getAlarmNo());
				alarmData.setAlarmState("G2");
				alarmService.updateAlarmState(alarmData);
			}
			catch(NullPointerException e) {
				AlarmVO savedAlarm = alarmService.saveAlarm(alarm);
			}
			
			
			// 테이블에 저장되지 않더라도 알람은 가도록 기능 분리
			messagingTemplate.convertAndSendToUser(userId, "/queue/alarms", alarm);
			System.out.println("Sent alarm to user: " + userId);
		} else {
			System.err.println("User ID is null. Cannot send alarm.");
		}

	}
	
	@GetMapping("alarms")
	public String alarms(@RequestParam(name = "receiverNo", required = false) Integer receiverNo, Model model) {
	    
		
		// 1. 기능 수행
		List<AlarmVO> alarmList = alarmService.alarmList(receiverNo);

	    // 2. 클라이언트에 전달할 데이터 담기
	    model.addAttribute("alarmList", alarmList);

	    // 3. 데이터를 출력할 페이지 결정
	    return "main/alarms";
	}
	
	@PostMapping("updateAlarmState")
    public ResponseEntity<Map<String, String>> updateAlarmState(@RequestBody AlarmVO alarm) {
        // alarmNo와 alarmState를 이용해 데이터베이스에서 해당 알람의 상태를 업데이트
        Map<String,String> resultMap = new HashMap<>();
		boolean result = alarmService.updateAlarmState(alarm);
        if(result) {        	
        	 resultMap.put("result", "success");
             return ResponseEntity.ok(resultMap); // HTTP 200 OK 상태 코드 반환
         } else {
             resultMap.put("result", "error");
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap); // HTTP 500 상태 코드 반환
         }

    }
}