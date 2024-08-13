package com.ecobank.app.score.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.score.mapper.ScoreMapper;
import com.ecobank.app.score.service.ScoreService;
@Service
public class ScoreServiceImpl implements ScoreService{
	private ScoreMapper scoreMapper;
	
	@Autowired
	ScoreServiceImpl(ScoreMapper scoreMapper){
		this.scoreMapper = scoreMapper;
	}
	
	@Override
	public int selectTotalUseScore() {
		// TODO Auto-generated method stub
		return this.scoreMapper.selectTotalUseScore();
	}



	@Override
	public int getScoreUsableByUserId(String useId) {
		// TODO Auto-generated method stub
		return scoreMapper.selectScoreUsableByUserId(useId);
	}



	@Override
	public int selectTotalScore() {
		// TODO Auto-generated method stub
		return scoreMapper.selectTotalScore();
	}

	@Override
	public int getChallEnterUserCount() {
		// TODO Auto-generated method stub
		return scoreMapper.selectChallEnterUserCount();
	}
}
