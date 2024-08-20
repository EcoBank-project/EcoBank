package com.ecobank.app.admin.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.QnA.service.QnaVO;
import com.ecobank.app.sns.service.SnsVO;

public interface AdminService {
    
    // 유저 목록 조회
    List<UserVO> UserList();
    
    // 챌린지 신고 목록 조회 
    List<ChallDeclareVO> ChallDeclareList();
    

    
    //sns 댓글 신고 관리
    List<SnsDeclareVO> SnsReplyDeclareList();
    
    
    //유저 회원수
    int getusers();
    
    // 오늘 가입한 회원 수 조회
    int getcreaTeat();
    
    // 회원 상태 업데이트
    int updateUserState(String useId, String userState);
   
    int updatefeedState(int feedNo, String feedState);
    
    //sns피드 신고 횟수
    int getCountByFeedNo(int feedNo);
    //sns댓글 신고 횟수
    int getCountByReplyNo(int replyNo);
    //챌린지 신고 횟수
    int getCountBychallNos(int confirmNo);
    
    // 프로시저 호출
    void UpdateSnsState();
    
    void UpdateChallengeUserState();
    
    //챌린지 신고 내용 조회 
    Map<String,Object> selectChallDeclare(int confirmNo);

	List<adminSnsVO> selectSns(adminSnsVO adminSnsVO);
	
	List<Map<String, Object>> SnsDeclareList(int feedNo);
	//sns 댓글 조회
	List<adminSnsVO> selectRepliesByFeedNo(int feedNo);
	
	//QNA 전체 조회
	List<QnaVO> qnaUser();
	
	//QNA 단건 조회
	QnaVO qnaSelectInfo(int qnaNo);	
	
	//QNA 답글 조회
	 QnaVO qnaReplySelect(int qnaNo);

	//QNA 답글 등록 
	int insertqnareplyInfo(QnaVO qnaVo);
	
 	//QNA 답글 삭제 
	 int deleteqnadeclare(int qnaVo);
}
