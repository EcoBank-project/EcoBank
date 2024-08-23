package com.ecobank.app.alarm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.alarm.mapper.AlarmMapper;
import com.ecobank.app.alarm.service.AlarmService;
import com.ecobank.app.alarm.service.AlarmVO;

@Service
public class AlarmServiceImpl implements AlarmService{
	
	@Autowired
    private AlarmMapper alarmMapper; // MyBatis Mapper

	
    public AlarmVO saveAlarm(AlarmVO alarm) {
        alarmMapper.insertAlarm(alarm); // DB에 알람 저장
        return alarm;
    }

	@Override
	public String getUserIdFromConfirmNo(Integer confirmNo) {
		// TODO Auto-generated method stub
		System.out.println("서비스 호출");
		return alarmMapper.getUserIdFromConfirmNo(confirmNo);
	}

	@Override
	public String getUserIdFromfeedNo(Integer feedNo) {
		// TODO Auto-generated method stub
		return alarmMapper.getUserIdFromfeedNo(feedNo);
	}
	@Override
	public boolean checkAlarmExists(AlarmVO alarm) {
        // 알람이 이미 존재하는지 확인
        int count = alarmMapper.checkAlarmExists(alarm);
        return count > 0;
    }

	@Override
	public List<AlarmVO> alarmList(Integer receiverNo) {
		// TODO Auto-generated method stub
		return alarmMapper.alarmList(receiverNo);
	}

	@Override
	public boolean updateAlarmState(AlarmVO alarm) {
        // alarmNo에 해당하는 알람의 상태를 업데이트하는 로직
		int result = alarmMapper.updateAlarmState(alarm);
        return result>0;
    }
}
