package com.ecobank.app.users.service;

import lombok.Data;

@Data
public class UserStatistics {

	private Integer totalScore;
	private Integer followerCount;
	private Integer followingCount;

	public UserStatistics(Integer totalScore, Integer followerCount, Integer followingCount) {
		this.totalScore = totalScore;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
	}

}
