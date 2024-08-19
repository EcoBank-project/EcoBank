package com.ecobank.app.home.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.home.mapper.HomeMapper;
import com.ecobank.app.home.service.ChallengeVO;
import com.ecobank.app.home.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService{

private HomeMapper homeMapper;
	
	@Autowired
	public HomeServiceImpl(HomeMapper homeMapper) {
		this.homeMapper = homeMapper;
	}
	
	@Override
	public List<ChallengeVO> getTopFiveFinishedChallenges() {
		// TODO Auto-generated method stub
		return homeMapper.getTopFiveFinishedChallenges();
	}

	@Override
	public ChallengeVO getMostPopularChallenge() {
		// TODO Auto-generated method stub
		return homeMapper.getMostPopularChallenge();
	}

	@Override
	public List<ChallengeVO> getOverSoonChallenges() {
		// TODO Auto-generated method stub
		return homeMapper.getOverSoonChallenges();
	}

}
