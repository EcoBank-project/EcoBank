package com.ecobank.app.alarm.service;

import java.util.List;

public interface AlarmService {
	public List<AlarmVO> alarmList(Integer receiverNo);
	public AlarmVO saveAlarm(AlarmVO alarm);
	public String getUserIdFromConfirmNo(Integer confirmNo);
	public String getUserIdFromfeedNo(Integer feedNo);
	public AlarmVO checkAlarmExists(AlarmVO alarm);
	public boolean updateAlarmState(AlarmVO alarm);
	public String getUserIdFromFollowingNo(int followingNo);
}
