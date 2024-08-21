package com.ecobank.app.QnA.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.QnA.mapper.QnaMapper;
import com.ecobank.app.QnA.service.QnaService;
import com.ecobank.app.QnA.service.QnaVO;

@Service
public class QnaServiceImpl implements QnaService {

    @Autowired
    private QnaMapper qnaMapper;

    // QNA 목록 조회
    @Override
    public List<QnaVO> qnaUserList() {
        List<QnaVO> qnaList = qnaMapper.qnaUserList();

        for (QnaVO qna : qnaList) {
            // 각 QNA에 대해 답글이 있는지 확인
            List<QnaVO> replies = qnaMapper.qnaReplyList(qna.getQnaNo());
            if (replies != null && !replies.isEmpty()) {
                qna.setReplyStatus("답변완료");
            } else {
                qna.setReplyStatus("미답변");
            }
        }

        return qnaList;
    }

    // QNA 등록
    @Override
    public int insertqnaInfo(QnaVO qnaVO) {
        return qnaMapper.insertqnaInfo(qnaVO);
    }

    // QNA 삭제
    @Override
    public int qnaDelete(int qnaNo) {
        return qnaMapper.qnaDelete(qnaNo);
    }

    // QNA 단건 조회
    @Override
    public QnaVO qnaSelectInfo(int qnaNo) {
        return qnaMapper.qnaSelectInfo(qnaNo);
    }

    // QNA 답글 조회
    @Override
    public List<QnaVO> qnaReplyList(int qnaNo) {
        return qnaMapper.qnaReplyList(qnaNo);
    }

	@Override
	public int updateQnaInfo(QnaVO qnaVo) {
		// TODO Auto-generated method stub
		return qnaMapper.updateQnaInfo(qnaVo);
	}
}
