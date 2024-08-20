package com.ecobank.app.home.service;

import java.util.Date;

import lombok.Data;

@Data
public class RankingVO {
	String useId; 
    String password; 
    String nickname; 
    Date createat; 
    String tell; 
    String profileImg; 
    String resp; 
    String country; 
    int userNo; 
    String userState; 
    String provider; 
    String providerId; 
    Date confirmCreateat;
    int useScore;
}
