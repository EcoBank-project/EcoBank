package com.ecobank.app.admin.service.impl;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.QnA.service.QnaVO;
import com.ecobank.app.admin.mapper.AdminMapper;
import com.ecobank.app.admin.service.AdminService;
import com.ecobank.app.admin.service.ChallDeclareVO;
import com.ecobank.app.admin.service.SnsDeclareVO;
import com.ecobank.app.admin.service.UserVO;
import com.ecobank.app.admin.service.adminSnsVO;

@Service
public class AdminServiceImpl implements AdminService {
    
    // AdminMapper 인스턴스를 주입받습니다.
    @Autowired
    AdminMapper adminMapper;

    // 유저 목록을 조회합니다.
    @Override
    public List<UserVO> UserList() {
        return adminMapper.userList();
    }

    // 전체 회원 수를 반환합니다.
    @Override
    public int getusers() {
        return adminMapper.getusers();
    }

    // 오늘 가입한 회원 수를 반환합니다.
    @Override
    public int getcreaTeat() {
        return adminMapper.getcreaTeat();
    }

    // 특정 유저의 상태를 업데이트합니다.
    @Override
    public int updateUserState(String useId, String userState) {
        return adminMapper.updateUserState(useId, userState);
    }

    // 챌린지 신고 목록을 조회합니다.
    @Override
    public List<ChallDeclareVO> ChallDeclareList() {
        return adminMapper.ChallDeclareList();
    }

    // SNS 댓글 신고 목록을 조회합니다.
    @Override
    public List<SnsDeclareVO> SnsReplyDeclareList() {
        return adminMapper.SnsReplyDeclareList();
    }

    // 특정 피드의 상태를 업데이트합니다.
    @Override
    public int updatefeedState(int feedNo, String feedState) {
        return adminMapper.updatefeedState(feedNo, feedState);
    }

    // 특정 피드 번호에 대한 신고 횟수를 반환합니다.
    @Override
    public int getCountByFeedNo(int feedNo) {
        return adminMapper.getCountByFeedNo(feedNo);
    }

    // 특정 댓글 번호에 대한 신고 횟수를 반환합니다.
    @Override
    public int getCountByReplyNo(int replyNo) {
        return adminMapper.getCountByReplyNo(replyNo);
    }

    // 특정 챌린지 번호에 대한 신고 횟수를 반환합니다.
    @Override
    public int getCountBychallNos(int confirmNo) {
        return adminMapper.getCountBychallNos(confirmNo);
    }

    // SNS 상태를 업데이트하는 MyBatis 프로시저를 호출합니다.
    @Override
    public void UpdateSnsState() {
        adminMapper.UpdateSnsState();
    }

    // 챌린지 유저 상태를 업데이트하는 MyBatis 프로시저를 호출합니다.
    @Override
    public void UpdateChallengeUserState() {
        adminMapper.UpdateChallengeUserState();
    }

    // 챌린지 신고 내용을 조회합니다.
    @Override
    public Map<String, Object> selectChallDeclare(int confirmNo) {
        return adminMapper.selectChallDeclare(confirmNo);
    }

    // SNS 목록을 조회합니다.
    @Override
    public List<adminSnsVO> selectSns(adminSnsVO adminSnsVO) {
        return adminMapper.selectSns(adminSnsVO);
    }

    // 특정 피드 번호에 대한 SNS 신고 목록을 조회합니다.
    @Override
    public List<Map<String, Object>> SnsDeclareList(int feedNo) {
        return adminMapper.SnsDeclareList(feedNo);
    }

    // QNA 목록을 조회합니다.
    @Override
    public List<QnaVO> qnaUser() {
        List<QnaVO> qnaList = adminMapper.qnaUser();
        for (QnaVO qna : qnaList) {
            // 답글 여부를 확인하여 설정
            QnaVO reply = adminMapper.qnaReplySelect(qna.getQnaNo());
            qna.setHasReply(reply != null);
        }
        return qnaList;
    }

    // 특정 QNA 번호에 대한 정보를 조회합니다.
    @Override
    public QnaVO qnaSelectInfo(int qnaNo) {
        return adminMapper.qnaSelectInfo(qnaNo);
    }

    // 특정 피드 번호에 대한 댓글 목록을 조회합니다.
    @Override
    public List<adminSnsVO> selectRepliesByFeedNo(int feedNo) {
        return adminMapper.selectRepliesByFeedNo(feedNo);
    }

    // QNA에 대한 답글을 등록합니다.
    @Override
    public int insertqnareplyInfo(QnaVO qnaVo) {
        return adminMapper.insertqnareplyInfo(qnaVo);
    }

    // QNA를 삭제합니다.
    @Override
    public int deleteqnadeclare(int qnaVo) {
        return adminMapper.deleteqnadeclare(qnaVo);
    }

    // 특정 QNA 번호에 대한 답글을 조회합니다.
    @Override
    public QnaVO qnaReplySelect(int qnaNo) {
        return adminMapper.qnaReplySelect(qnaNo);
    }

    // 정지된 회원 수를 반환합니다.
    @Override
    public int stateCount() {
        return adminMapper.stateCount();
    }

    // QNA 답글 정보를 업데이트합니다.
    @Override
    public int updateQnaReplyInfo(QnaVO qnaVO) {
        return adminMapper.updateQnaReplyInfo(qnaVO);
    }
    //QNA 댓글 달아야하는 QNA글 수 반환합니다.
    @Override
    public int qnaReplynocount() {
    	return adminMapper.qnaReplynocount();
    }

    @Override
    public List<UserVO> userSysCreateat() {
        return adminMapper.userSysCreateat();
    }

	@Override
	public List<UserVO> userState() {
		// TODO Auto-generated method stub
		return adminMapper.userState();
	}

	@Override
	public List<QnaVO> qnaReply() {
		// TODO Auto-generated method stub
		return adminMapper.qnaReply();
	}
	
}
