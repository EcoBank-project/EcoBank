package com.ecobank.app.users.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStatistics {

	private Integer totalScore;
	private Integer followerCount;
	private Integer followingCount;
	private String userProfile;

}
