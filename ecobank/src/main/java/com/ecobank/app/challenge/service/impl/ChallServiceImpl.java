package com.ecobank.app.challenge.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.challenge.mapper.ChallMapper;
import com.ecobank.app.challenge.service.ChallService;
import com.ecobank.app.challenge.service.ChallVO;
import com.ecobank.app.challenge.service.ReviewDTO;
import com.ecobank.app.common.service.Criteria;
@Service
public class ChallServiceImpl implements ChallService{
	private ChallMapper challMapper;
	
	@Autowired
	public ChallServiceImpl(ChallMapper challMapper) {
		this.challMapper = challMapper;
	}
	
	//전체조회
	@Override
	public List<ChallVO> challList(Criteria criteria) {
		return challMapper.selectChallAll(criteria);
	}
	
	//단건조회
	@Override
	public ChallVO challInfo(ChallVO challVO) {
		return challMapper.selectChallInfo(challVO);
	}

	//챌린지 등록
	@Override
	public int challInsert(ChallVO challVO) {
		int result = challMapper.insertChallInfo(challVO);
		return result == 1 ? challVO.getChallNo() : -1;
	}

	//챌린지 수정
	@Override
	public Map<String, Object> challUpdate(ChallVO challVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = challMapper.updateChallInfo(challVO);
		if(result > 0) { //수정되었으면 
			isSuccessed = true; //성공
		}
		
		map.put("result", isSuccessed); //수정되었을때 true,실패하면 false 반환
		map.put("target", challVO); //수정된 내용
		
		return map;
	}

	@Override
	public int challDelete(int challNo) {
		return challMapper.deleteChallInfo(challNo);
	}
	
	//점수 목록 조회
	@Override
	public List<Map<String, Object>> scoreList(Criteria criteria) {
		return challMapper.selectScoreAll(criteria);
	}
	
	//점수 목록 개수 가져오기
	@Override
	public int getScoreTotal(Criteria criteria) {
		return challMapper.getScoreTotal(criteria);
	}
	
	//챌린지 목록 개수(페이징) 
	@Override
	public int getTotalByState(Criteria criteria) {
		return challMapper.getTotalByState(criteria);
	}
	
	//챌린지 총개수 - 회원
	@Override
	public int countChallengesByState(ChallVO challVO) {
		return challMapper.countAllChallenges(challVO);
	}

	@Override
	public List<ChallVO> getDList(Criteria criteria) {
		return challMapper.getChallList(criteria);
	}
	
	//챌린지 개수 가져오기 - 관리자
	@Override
	public int getTotal(Criteria criteria) {
		return challMapper.getTotal(criteria);
	}

	//챌린지 좋아요 개수
	@Override
	public int challLikeCnt(int challNo) {
		return challMapper.challLikeTotalCnt(challNo);
	}

	//챌린지 좋아요 여부
	@Override
	public int challLikeStatus(int userNo, int challNo) {
		return challMapper.challLikeStatus(userNo, challNo);
	}

	//챌린지 좋아요 등록
	@Override
	public int challLikeInsert(ChallVO challVO) {
		return challMapper.insertChallLike(challVO);
	}

	//챌린지 좋아요 삭제
	@Override
	public int challLikeDelete(int userNo, int challNo) {
		return challMapper.deleteChallLike(userNo, challNo);
	}

	//챌린지 정렬
	@Override
	public List<ChallVO> challengeSort(int userNo, int select) {
		if(select == 1) { //날짜순
			return challMapper.orderByDate();
		}else if(select == 2) { //좋아요
			return challMapper.orderByLike();
		}else { //참가
			return challMapper.orderByEnter(userNo);
		}
	}
	
	//후기 목록
	@Override
	public List<ReviewDTO> reviewList(ChallVO challVO) {
		return challMapper.selectReviewAll(challVO);
	}
	
	//후기 평균 별점 구하기
	@Override
	public Double getAvgStar(int challNo) {
		Double avgStar = challMapper.getAvgStar(challNo);
		return avgStar;
	}
	
	//후기 등록 여부
	@Override
	public int reviewStatus(int userNo, int challNo) {
		return challMapper.reviewStatus(userNo, challNo);
	}

	//후기 등록
	@Override
	public int reviewInsert(ReviewDTO reviewDTO) {
		int result = challMapper.insertReview(reviewDTO);
		//System.out.println(result + "후기 결과에 뭐있나");
		return result == 1 ? reviewDTO.getReviewNo() : -1;
	}

	//후기 삭제
	@Override
	public int reviewDelete(int userNo, int reviewNo) {
		int otherUserNo = challMapper.findUserNoByReviewNo(reviewNo);
		if(userNo == otherUserNo) {
			int result = challMapper.deleteReview(reviewNo);
			//System.out.println(result + "결과값");
			return result;
		}else {
			return 0;
		}
	}

}
