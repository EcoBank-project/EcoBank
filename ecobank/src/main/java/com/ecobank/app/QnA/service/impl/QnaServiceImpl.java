package com.ecobank.app.QnA.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.QnA.mapper.QnaMapper;
import com.ecobank.app.QnA.service.QnaService;
import com.ecobank.app.QnA.service.QnaVO;

@Service
public class QnaServiceImpl implements QnaService{

	@Autowired
	QnaMapper qnaMapper;
	// 전체조회
	@Override
	public List<QnaVO> qnaUserList() {
		return qnaMapper.qnaUserList();
	}
	//등록
	@Override
	public int insertqnaInfo(QnaVO qnaVO) {
		return qnaMapper.insertqnaInfo(qnaVO);
	}
	@Override
	public int qnaDelete(int qnaVo) {
		return qnaMapper.qnaDelete(qnaVo);
	}

	@Override
	public QnaVO qnaSelectInfo(int qnaNo) {
		// TODO Auto-generated method stub
		return qnaMapper.qnaSelectInfo(qnaNo);
	}

	@Override
	public List<QnaVO> qnaReplyList(int qnaNo) {
		// TODO Auto-generated method stub
		return qnaMapper.qnaReplyList(qnaNo);
	}

}
