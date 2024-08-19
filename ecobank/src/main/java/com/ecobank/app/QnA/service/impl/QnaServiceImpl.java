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
		// TODO Auto-generated method stub
		return qnaMapper.qnaUserList();
	}
	//등록
	@Override
	public int insertqnaInfo(QnaVO qnaVO) {
		// TODO Auto-generated method stub
		return qnaMapper.insertqnaInfo(qnaVO);
	}
	//단건조회 
	@Override
	public QnaVO qnaSelectInfo(int qnaNo) {
		// TODO Auto-generated method stub
		return qnaMapper.qnaSelectInfo(qnaNo);
	}
	@Override
	public int qnaDelete(int qnaVo) {
		// TODO Auto-generated method stub
		return qnaMapper.qnaDelete(qnaVo);
	}
	


	

}
