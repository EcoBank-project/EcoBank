package com.ecobank.app.score.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecobank.app.intro.service.CarbUserVO;
import com.ecobank.app.score.mapper.ScoreMapper;
import com.ecobank.app.score.service.ScoreService;
import com.ecobank.app.score.service.ScoreVO;
import com.ecobank.app.security.service.LoginUserVO;

@Service
public class ScoreServiceImpl implements ScoreService {
	private ScoreMapper scoreMapper;

	@Autowired
	ScoreServiceImpl(ScoreMapper scoreMapper) {
		this.scoreMapper = scoreMapper;
	}

	@Override
	public int selectTotalUseScore() {
		// TODO Auto-generated method stub
		return this.scoreMapper.selectTotalUseScore();
	}

	@Override
	public String getScoreUsableByUserId(String useId) {
		// TODO Auto-generated method stub
		String scoreUsable = scoreMapper.selectScoreUsableByUserId(useId);
		return scoreUsable==null?"0":scoreUsable;
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

	@Override
	public int insertToUseScore(ScoreVO scoreVO) {
		int result = scoreMapper.insertToUseScore(scoreVO);
		return result == 1 ? scoreVO.getScoreNo() : -1;
	}

	@Override
	public String getUserNoFromId(CarbUserVO userVO) {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUserVO loginUser = null;

		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof LoginUserVO) {
				loginUser = (LoginUserVO) principal;
			}
		}

		if (loginUser != null) {
			// 로그인된 사용자의 정보 사용 예: 사용 가능한 점수, 사용자 이름 등
//		        int userAvailableScore = scoreService.getUserAvailableScore(loginUser.getUserNO());
			/*
			 * model.addAttribute("userAvailableScore", userAvailableScore);
			 * model.addAttribute("username", loginUser.getUsername());
			 * model.addAttribute("nickname", loginUser.getNickname());
			 */
			userVO.setUseId(loginUser.getUsername());
		} else {
			userVO.setUseId("dummyId");
		}
		return scoreMapper.getUserNoFromId(userVO);
	}

	@Override
	public String getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUserVO loginUser = null;
		String userId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof LoginUserVO) {
				loginUser = (LoginUserVO) principal;
			}
		}

		if (loginUser != null) {
			userId = loginUser.getUsername();
		} else {
			userId = "dummyId";
		}
		return userId;
	}
}
