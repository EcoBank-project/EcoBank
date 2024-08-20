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
import com.ecobank.app.sns.service.SnsVO;

@Service
public class AdminServiceImpl implements AdminService {
	
	//AdminServiceimpl.java

	
    @Autowired
    AdminMapper adminMapper;

    // 유저 목록 조회
    @Override
    public List<UserVO> UserList() {
        return adminMapper.userList();
    }

    // 총 회원 수 
    @Override
    public int getusers() {
        return adminMapper.getusers();
    }

    // 오늘 가입한 회원 수 조회
    @Override
    public int getcreaTeat() {
        return adminMapper.getcreaTeat();
    }

    // 회원 상태 업데이트 
    @Override
    public int updateUserState(String useId, String userState) {
        return adminMapper.updateUserState(useId, userState);
    }

    // 챌린지 신고 목록 조회
    @Override
    public List<ChallDeclareVO> ChallDeclareList() {
        return adminMapper.ChallDeclareList();
    }

    //sns 댓글 신고 조회 
    @Override
    public List<SnsDeclareVO> SnsReplyDeclareList() {
        return adminMapper.SnsReplyDeclareList();
    }


    @Override
    public int updatefeedState(int feedNo, String feedState) {
        return adminMapper.updatefeedState(feedNo, feedState);
    }

    @Override
    public int getCountByFeedNo(int feedNo) {
        return adminMapper.getCountByFeedNo(feedNo);
    }

    @Override
    public int getCountByReplyNo(int replyNo) {
        return adminMapper.getCountByReplyNo(replyNo);
    }

    @Override
    public int getCountBychallNos(int confirmNo) {
        return adminMapper.getCountBychallNos(confirmNo);
    }

    // MyBatis 프로시저 호출
    @Override
    public void UpdateSnsState() {
        adminMapper.UpdateSnsState();
    }

	@Override
	public void UpdateChallengeUserState() {
		
		adminMapper.UpdateChallengeUserState();
	}
    //챌린지 신고 내용 조회 

	@Override
	public Map<String,Object> selectChallDeclare(int confirmNo) {
		return adminMapper.selectChallDeclare(confirmNo);
	}



	@Override
	public List<adminSnsVO> selectSns(adminSnsVO adminSnsVO) {
		// TODO Auto-generated method stub
		return adminMapper.selectSns(adminSnsVO);
	}
	//sns 신고 조회 
	@Override
	public List<Map<String, Object>> SnsDeclareList(int feedNo) {
	    return adminMapper.SnsDeclareList(feedNo);
	}

	
	// 전체조회
	@Override
	public List<QnaVO> qnaUser() {
		// TODO Auto-generated method stub
		return adminMapper.qnaUser();
	}
	
	@Override
	public QnaVO qnaSelectInfo(int qnaNo) {
		// TODO Auto-generated method stub
		return adminMapper.qnaSelectInfo(qnaNo);
	}

	@Override
	public List<adminSnsVO> selectRepliesByFeedNo(int feedNo) {
	    return adminMapper.selectRepliesByFeedNo(feedNo);
	}


	//답글 등록
	@Override
	public int insertqnareplyInfo(QnaVO qnaVo) {
		// TODO Auto-generated method stub
		return adminMapper.insertqnareplyInfo(qnaVo);
	}

	@Override
	public int deleteqnadeclare(int qnaVo) {
		// TODO Auto-generated method stub
		return adminMapper.deleteqnadeclare(qnaVo);
	}

	@Override
	public QnaVO qnaReplySelect(int qnaNo) {
		// TODO Auto-generated method stub
		return adminMapper.qnaReplySelect(qnaNo);
	}

}
