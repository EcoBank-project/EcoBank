package com.ecobank.app.alarm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.alarm.service.AlarmVO;

public interface AlarmMapper {
	
	// insert전 기존에 존재하는 알람인지 확인(알람 유일성)
	AlarmVO checkAlarmExists(AlarmVO alarm);
	
	// 알람 저장
    public void insertAlarm(AlarmVO alarm);
    
    // 챌린지 인증 작성자 아이디 확인용
    public String getUserIdFromConfirmNo(@Param("confirmNo") Integer confirmNo);
    
    // 피드 작성자 아이디 확인용
    public String getUserIdFromfeedNo(@Param("feedNo") Integer feedNo);
    
    // 알람리스트 조회
    public List<AlarmVO> alarmList(@Param("receiverNo") Integer receiverNo);
    
    // 알람 읽음상태 업데이트
    public int updateAlarmState(AlarmVO alarm);

	public String getUserIdFromFollowingNo(@Param("followingNo") Integer followingNo);
}
