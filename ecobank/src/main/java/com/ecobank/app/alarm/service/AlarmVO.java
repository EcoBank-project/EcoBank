package com.ecobank.app.alarm.service;

import java.util.Date;

import lombok.Data;

@Data
public class AlarmVO {
    private int alarmNo;
    private int receiverNo;
    private String alarmType;
    private String alarmContent;
    private String alarmCode;
    private Date alarmCreateAt;
    private String alarmState;
    private int alarmRefNo;
    private int userNo;
    private String senderNickname;
}