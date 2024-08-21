package com.ecobank.app.alarm.service.impl;

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
}
