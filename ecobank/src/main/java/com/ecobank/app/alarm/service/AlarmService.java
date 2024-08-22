package com.ecobank.app.alarm.service;

public interface AlarmService {
	public AlarmVO saveAlarm(AlarmVO alarm);
	public String getUserIdFromConfirmNo(Integer confirmNo);
}
