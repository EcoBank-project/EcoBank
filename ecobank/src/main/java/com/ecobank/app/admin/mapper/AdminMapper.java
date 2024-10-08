package com.ecobank.app.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecobank.app.QnA.service.QnaVO;
import com.ecobank.app.admin.service.ChallDeclareVO;
import com.ecobank.app.admin.service.SnsDeclareVO;
import com.ecobank.app.admin.service.UserVO;
import com.ecobank.app.admin.service.adminSnsVO;
import com.ecobank.app.sns.service.SnsVO;

public interface AdminMapper {
    
    // 유저 목록 조회
    List<UserVO> userList();
    
    //가입한 회원수 조회
    int getusers();
    // 오늘 가입한 회원 수 조회
    int getcreaTeat();
    
    // 회원 상태 업데이트
    int updateUserState(@Param("useId") String useId, @Param("userState") String userState);
    
    //챌린지 신고 조회 
    List<ChallDeclareVO> ChallDeclareList();
    
    
    //sns 댓글 신고 조회 
    List<SnsDeclareVO> SnsReplyDeclareList();
    
    //sns 상태 변환 업데이트
    int updatefeedState(@Param("feedNo") int feedNo, @Param("feedState") String feedState);
       
    //sns 피드 신고당한횟수
    int getCountByFeedNo(int feedNo);
    
    //sns 댓글 신고당한횟수
    int getCountByReplyNo(int replyNo);
    
    //챌린지 신고 횟수
    int getCountBychallNos(int confirmNo);
    
    //프로시저 호출
    void UpdateSnsState();
    void UpdateChallengeUserState();
    
   //챌린지 신고 내용 조회 
   Map<String,Object> selectChallDeclare(int confirmNo);
   
   List<adminSnsVO> selectSns(adminSnsVO adminSnsVO);
   
   Map<String,Object> selectSnsreply(int feedNo);
   
   //sns 댓글 조회
   List<Map<String, Object>> SnsDeclareList(@Param("feedNo") int feedNo);
   
   
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
	 
	//정지되회원수
	 int stateCount();
	//QNA댓글 수정 
	 int updateQnaReplyInfo(QnaVO qnaVO);
	 
	 //QNA댓글 필요한글 수
	 int qnaReplynocount();
	 
	 // 오늘 회원가입한 유저 조회
	 List<UserVO> userSysCreateat();	 
	 
	 // 오늘 회원가입한 유저 조회
	 List<UserVO> userState();
	 
	 List<QnaVO>  qnaReply();
	  
}
