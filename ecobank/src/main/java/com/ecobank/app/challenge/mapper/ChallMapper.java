package com.ecobank.app.challenge.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.challenge.service.ChallVO;
import com.ecobank.app.challenge.service.ReviewDTO;
import com.ecobank.app.common.service.Criteria;

public interface ChallMapper {
	//챌린지 전체 조회 - 관리자용
	public List<ChallVO> selectChallAll(Criteria criteria); //나중에 검색(x), 페이징 조건(o) 매개값으로 넣어줘야함
	
	//챌린지 목록 개수(페이징)
	public int getTotal(Criteria criteria);
	
	//챌린지 조회 - 회원용(상태값에 따라)
	//public List<ChallVO> getChallList();
	public List<ChallVO> getChallList(Criteria criteria);
	
	//챌린지 목록 개수(페이징) - 상태에 따른
	public int getTotalByState(Criteria criteria);
	
	//챌린지 개수 가져오기 - 회원 / cnt
	public int countAllChallenges(ChallVO challVO);
	//public int countAllChallenges(Criteria criteria);
	
	//챌린지 단건 조회
	public ChallVO selectChallInfo(ChallVO challVO);
	
	//챌린지 D2에 참여한 인원 카운트
	public int enterUserCount(int challNo);

	//챌린지 좋아요 전체 개수
	public int challLikeTotalCnt(int challNo);
	
	//챌린지 좋아요 했는지 안했는지 여부
	public int challLikeStatus(int userNo, int challNo);
	
	//챌린지 좋아요 등록
	public int insertChallLike(ChallVO challVO);
	
	//챌린지 좋아요 삭제
	public int deleteChallLike(int userNo, int challNo);
	
	//챌린지 등록
	public int insertChallInfo(ChallVO challVO);
	
	//챌린지 수정
	public int updateChallInfo(ChallVO challVO);
	
	//챌린지 사진 update
	public int reUpdateChall(ChallVO challVO);
	
	//챌린지 삭제
	public int deleteChallInfo(int challNo);
	
	//인증 회원 점수 목록
	public List<Map<String, Object>> selectScoreAll(Criteria criteria);
	
	//점수 목록 개수 가져오기
	public int getScoreTotal(Criteria criteria);
	
	//좋아요순 정렬
	public List<ChallVO> orderByLike();
	
	//내가 참여 중인 챌린지순 정렬
	public List<ChallVO> orderByEnter(int userNo);
	
	//최신 날짜 순 정렬
	public List<ChallVO> orderByDate();
	
	//챌린지 후기 목록
	public List<ReviewDTO> selectReviewAll(ChallVO challVO);
	
	//챌린지 후기 평균 별점 구하기
	public Double getAvgStar(int challNo);
	
	//챌린지 후기 등록 여부(결과가 있으면 1/ 참여한적 없으면 0)
	public int reviewStatus(@Param("userNo") int userNo, @Param("challNo") int challNo);
	
	//챌린지 후기 등록
	public int insertReview(ReviewDTO reviewDTO);
	
	//챌린지 후기 삭제하려고 후기 유저 번호 가져오기
	public int findUserNoByReviewNo(int reviewNo);
	
	//챌린지 후기 삭제
	public int deleteReview(int reviewNo);
	
}
