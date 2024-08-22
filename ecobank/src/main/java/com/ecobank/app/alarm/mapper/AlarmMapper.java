package com.ecobank.app.alarm.mapper;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.alarm.service.AlarmVO;

public interface AlarmMapper {
    public void insertAlarm(AlarmVO alarm);
    public String getUserIdFromConfirmNo(@Param("confirmNo") Integer confirmNo);
}
